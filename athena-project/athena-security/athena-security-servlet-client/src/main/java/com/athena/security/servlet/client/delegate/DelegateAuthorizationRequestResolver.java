package com.athena.security.servlet.client.delegate;

import jakarta.annotation.Resource;
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
     * 授权请求匹配器
     */
    private final AntPathRequestMatcher authorizationRequestMatcher = new AntPathRequestMatcher(AUTHORIZATION_REQUEST_BASE_URI + "/{" + REGISTRATION_ID + "}");
    /**
     * 自定义 OAuth2 授权请求器
     */
    @Resource
    private List<IAuthorizationRequestCustomizer> customizers;
    /**
     * 客户端注册库
     */
    @Resource
    private ClientRegistrationRepository clientRegistrationRepository;


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
        DefaultOAuth2AuthorizationRequestResolver delegate = getDelegate(clientRegistrationId);
        return delegate.resolve(request);
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
        DefaultOAuth2AuthorizationRequestResolver delegate = getDelegate(clientRegistrationId);
        return delegate.resolve(request, clientRegistrationId);
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
     * 获取委托
     *
     * @param clientRegistrationId 客户端注册标识
     * @return 委托
     */
    private DefaultOAuth2AuthorizationRequestResolver getDelegate(String clientRegistrationId) {
        DefaultOAuth2AuthorizationRequestResolver delegate = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, AUTHORIZATION_REQUEST_BASE_URI);
        // 自定义 OAuth2 授权请求器
        customizers.stream()
                .filter(customizer -> customizer.test(clientRegistrationId))
                .findFirst()
                .ifPresent(delegate::setAuthorizationRequestCustomizer);
        return delegate;
    }


}
