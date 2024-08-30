package com.athena.security.servlet.client.delegate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 委托授权请求解析器
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
     * 默认 OAuth2 授权请求解析器
     */
    private final DefaultOAuth2AuthorizationRequestResolver delegate;
    /**
     * 自定义 OAuth2 授权请求器
     */
    private final List<IAuthorizationRequestCustomizer> customizers;
    /**
     * 授权请求匹配器
     */
    private final AntPathRequestMatcher authorizationRequestMatcher;

    /**
     * 实例化一个委托授权请求解析器
     *
     * @param clientRegistrationRepository 客户端注册库
     * @param customizers                  自定义 OAuth2 授权请求器
     */
    public DelegateAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository,
                                                List<IAuthorizationRequestCustomizer> customizers) {
        this.customizers = customizers;
        this.authorizationRequestMatcher = new AntPathRequestMatcher(AUTHORIZATION_REQUEST_BASE_URI + "/{" + REGISTRATION_ID + "}");
        this.delegate = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, AUTHORIZATION_REQUEST_BASE_URI);
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
        // 自定义 OAuth2 授权请求器
        customizers.stream()
                .filter(customizer -> customizer.test(clientRegistrationId))
                .findFirst()
                .ifPresent(delegate::setAuthorizationRequestCustomizer);
        return delegate.resolve(request);
    }

    /**
     * 获取客户端注册标识
     *
     * @param request 请求
     * @return 客户端注册标识
     */
    private String getClientRegistrationId(HttpServletRequest request) {
        // 匹配请求
        if (authorizationRequestMatcher.matches(request)) {
            // 获取注册标识
            return authorizationRequestMatcher.matcher(request).getVariables().get(REGISTRATION_ID);
        }
        return null;
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
        // 自定义 OAuth2 授权请求器
        customizers.stream()
                .filter(customizer -> customizer.test(clientRegistrationId))
                .findFirst()
                .ifPresent(delegate::setAuthorizationRequestCustomizer);
        // 解析授权请求
        return delegate.resolve(request, clientRegistrationId);
    }
}
