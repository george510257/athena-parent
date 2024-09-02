package com.athena.security.servlet.client.customizer;

import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * OAuth2 登录自定义器
 *
 * @author george
 */
@Component
public class OAuth2LoginCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {
    /**
     * 授权端点自定义器
     */
    @Resource
    private AuthorizationEndpointCustomizer authorizationEndpointCustomizer;
    /**
     * 重定向端点自定义器
     */
    @Resource
    private RedirectionEndpointCustomizer redirectionEndpointCustomizer;
    /**
     * 令牌端点自定义器
     */
    @Resource
    private TokenEndpointCustomizer tokenEndpointCustomizer;
    /**
     * 用户信息端点自定义器
     */
    @Resource
    private UserInfoEndpointCustomizer userInfoEndpointCustomizer;

    /**
     * 自定义 OAuth2 登录配置
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity> configurer) {
        // 授权端点自定义器
        configurer.authorizationEndpoint(authorizationEndpointCustomizer);
        // 重定向端点自定义器
        configurer.redirectionEndpoint(redirectionEndpointCustomizer);
        // 令牌端点自定义器
        configurer.tokenEndpoint(tokenEndpointCustomizer);
        // 用户信息端点自定义器
        configurer.userInfoEndpoint(userInfoEndpointCustomizer);
    }
}
