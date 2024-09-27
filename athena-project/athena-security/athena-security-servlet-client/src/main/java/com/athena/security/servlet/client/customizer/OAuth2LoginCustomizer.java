package com.athena.security.servlet.client.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * OAuth2 登录自定义器
 *
 * @author george
 */
@Component
public class OAuth2LoginCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    /**
     * 委托授权请求解析器
     */
    @Resource
    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;
    /**
     * 委托授权码令牌响应客户端
     */
    @Resource
    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient;
    /**
     * 委托 OAuth2 用户信息服务
     */
    @Resource
    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;

    /**
     * 自定义 OAuth2 登录配置
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity> configurer) {
        // 登录页面
        configurer.loginPage(coreSecurityProperties.getRest().getLoginPage());
        // 授权端点自定义器
        configurer.authorizationEndpoint(this::authorizationEndpoint);
        // 重定向端点自定义器
        configurer.redirectionEndpoint(this::redirectionEndpoint);
        // 令牌端点自定义器
        configurer.tokenEndpoint(this::tokenEndpoint);
        // 用户信息端点自定义器
        configurer.userInfoEndpoint(this::userInfoEndpoint);
    }

    /**
     * 自定义 OAuth2 授权端点配置
     *
     * @param config 配置器
     */
    private void authorizationEndpoint(OAuth2LoginConfigurer<HttpSecurity>.AuthorizationEndpointConfig config) {
        // 自定义 OAuth2 授权请求解析器
        config.authorizationRequestResolver(authorizationRequestResolver);
    }

    /**
     * 重定向端点自定义器
     *
     * @param config 配置器
     */
    private void redirectionEndpoint(OAuth2LoginConfigurer<HttpSecurity>.RedirectionEndpointConfig config) {
    }

    /**
     * 自定义 OAuth2 令牌端点配置
     *
     * @param config 配置器
     */
    private void tokenEndpoint(OAuth2LoginConfigurer<HttpSecurity>.TokenEndpointConfig config) {
        // 自定义 OAuth2 授权码令牌响应客户端
        config.accessTokenResponseClient(accessTokenResponseClient);
    }

    /**
     * 自定义 OAuth2 用户信息端点配置
     *
     * @param config 配置器
     */
    private void userInfoEndpoint(OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig config) {
        // 自定义 OAuth2 用户信息服务
        config.userService(oauth2UserService);
    }
}
