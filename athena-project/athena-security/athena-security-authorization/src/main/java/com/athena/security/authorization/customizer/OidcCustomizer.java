package com.athena.security.authorization.customizer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcConfigurer;
import org.springframework.stereotype.Component;

/**
 * OIDC自定义器
 */
@Component
public record OidcCustomizer(
        ObjectProvider<ProviderConfigurationEndpointCustomizer> providerConfigurationEndpointCustomizer,
        ObjectProvider<LogoutEndpointCustomizer> logoutEndpointCustomizer,
        ObjectProvider<ClientRegistrationEndpointCustomizer> clientRegistrationEndpointCustomizer,
        ObjectProvider<UserInfoEndpointCustomizer> userInfoEndpointCustomizer) implements Customizer<OidcConfigurer> {

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcConfigurer configurer) {
        // OIDC提供者配置端点自定义
        providerConfigurationEndpointCustomizer.ifAvailable(configurer::providerConfigurationEndpoint);
        // OIDC登出端点自定义
        logoutEndpointCustomizer.ifAvailable(configurer::logoutEndpoint);
        // OIDC客户端注册端点自定义
        clientRegistrationEndpointCustomizer.ifAvailable(configurer::clientRegistrationEndpoint);
        // OIDC用户信息端点自定义
        userInfoEndpointCustomizer.ifAvailable(configurer::userInfoEndpoint);
    }
}
