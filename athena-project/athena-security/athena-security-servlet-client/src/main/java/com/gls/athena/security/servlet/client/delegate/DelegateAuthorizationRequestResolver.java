package com.gls.athena.security.servlet.client.delegate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * 委托授权请求解析器
 *
 * @author george
 */
@Component
public class DelegateAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    /**
     * 注册标识
     */
    private final static String REGISTRATION_ID = "registrationId";
    /**
     * 授权请求基础 URI
     */
    private final static String AUTHORIZATION_REQUEST_BASE_URI = "/oauth2/authorization";
    /**
     * 授权请求匹配器
     */
    private final static AntPathRequestMatcher REQUEST_MATCHER = new AntPathRequestMatcher(AUTHORIZATION_REQUEST_BASE_URI + "/{" + REGISTRATION_ID + "}");
    /**
     * 默认 OAuth2 授权请求解析器
     */
    private final DefaultOAuth2AuthorizationRequestResolver resolver;
    /**
     * 自定义 OAuth2 授权请求器
     */
    private final ObjectProvider<IAuthorizationRequestCustomizer> customizers;
    /**
     * 客户端注册库
     */
    private final ClientRegistrationRepository clientRegistrationRepository;

    /**
     * 构造函数
     *
     * @param clientRegistrationRepository 客户端注册库
     * @param customizers                  定制器
     */
    public DelegateAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository,
                                                ObjectProvider<IAuthorizationRequestCustomizer> customizers) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.resolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, AUTHORIZATION_REQUEST_BASE_URI);
        this.customizers = customizers;
    }

    /**
     * 解析授权请求
     *
     * @param request 请求
     * @return 授权请求
     */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        // 获取客户端注册标识
        String clientRegistrationId = getClientRegistrationId(request);
        // 自定义解析器
        resolver.setAuthorizationRequestCustomizer(builder -> customizerResolver(builder, request, clientRegistrationId));
        // 解析请求
        return resolver.resolve(request);
    }

    /**
     * 解析授权请求
     *
     * @param request              请求
     * @param clientRegistrationId 客户端注册标识
     * @return 授权请求
     */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        // 自定义解析器
        resolver.setAuthorizationRequestCustomizer(builder -> customizerResolver(builder, request, clientRegistrationId));
        // 解析请求
        return resolver.resolve(request, clientRegistrationId);
    }

    /**
     * 获取客户端注册标识
     *
     * @param request 请求
     * @return 客户端注册标识
     */
    private String getClientRegistrationId(HttpServletRequest request) {
        // 匹配请求
        if (REQUEST_MATCHER.matches(request)) {
            // 获取注册标识
            return REQUEST_MATCHER.matcher(request).getVariables().get(REGISTRATION_ID);
        }
        return null;
    }

    /**
     * 自定义解析器
     *
     * @param builder              构建器
     * @param request              请求
     * @param clientRegistrationId 客户端注册标识
     */
    private void customizerResolver(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request, String clientRegistrationId) {
        // 获取提供者
        String provider = clientRegistrationRepository.findByRegistrationId(clientRegistrationId)
                .getProviderDetails().getConfigurationMetadata().get("providerId").toString();
        // 自定义 OAuth2 授权请求器
        customizers.stream()
                .filter(customizer -> customizer.test(provider))
                .findFirst()
                .ifPresent(customizer -> customizer.accept(builder, request));
    }

}
