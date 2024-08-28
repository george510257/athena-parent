package com.athena.security.servlet.authorization.customizer;

import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcConfigurer;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * OIDC自定义器
 */
@Component
public class OidcCustomizer implements Customizer<OidcConfigurer> {

    /**
     * OIDC提供者配置端点自定义器
     */
    @Resource
    private Optional<OidcProviderConfigurationEndpointCustomizer> oidcProviderConfigurationEndpointCustomizer;
    /**
     * OIDC登出端点自定义器
     */
    @Resource
    private Optional<OidcLogoutEndpointCustomizer> oidcLogoutEndpointCustomizer;
    /**
     * OIDC客户端注册端点自定义器
     */
    @Resource
    private Optional<OidcClientRegistrationEndpointCustomizer> oidcClientRegistrationEndpointCustomizer;
    /**
     * OIDC用户信息端点自定义器
     */
    @Resource
    private Optional<OidcUserInfoEndpointCustomizer> oidcUserInfoEndpointCustomizer;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcConfigurer configurer) {
        // OIDC提供者配置端点自定义
        oidcProviderConfigurationEndpointCustomizer.ifPresent(configurer::providerConfigurationEndpoint);
        // OIDC登出端点自定义
        oidcLogoutEndpointCustomizer.ifPresent(configurer::logoutEndpoint);
        // OIDC客户端注册端点自定义
        oidcClientRegistrationEndpointCustomizer.ifPresent(configurer::clientRegistrationEndpoint);
        // OIDC用户信息端点自定义
        oidcUserInfoEndpointCustomizer.ifPresent(configurer::userInfoEndpoint);
    }
}
