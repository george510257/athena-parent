package com.athena.security.authorization.customizer;

import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * OAuth2授权服务器自定义器
 */
@Component
public class OAuth2AuthorizationServerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    /**
     * 客户端认证自定义器
     */
    @Resource
    private Optional<ClientAuthenticationCustomizer> clientAuthenticationCustomizer;
    /**
     * 授权服务器元数据端点自定义器
     */
    @Resource
    private Optional<AuthorizationServerMetadataEndpointCustomizer> authorizationServerMetadataEndpointCustomizer;
    /**
     * 授权端点自定义器
     */
    @Resource
    private Optional<AuthorizationEndpointCustomizer> authorizationEndpointCustomizer;
    /**
     * 令牌端点自定义器
     */
    @Resource
    private Optional<TokenEndpointCustomizer> tokenEndpointCustomizer;
    /**
     * 令牌验证端点自定义器
     */
    @Resource
    private Optional<TokenIntrospectionEndpointCustomizer> tokenIntrospectionEndpointCustomizer;
    /**
     * 令牌撤销端点自定义器
     */
    @Resource
    private Optional<TokenRevocationEndpointCustomizer> tokenRevocationEndpointCustomizer;
    /**
     * 设备授权端点自定义器
     */
    @Resource
    private Optional<DeviceAuthorizationEndpointCustomizer> deviceAuthorizationEndpointCustomizer;
    /**
     * 设备验证端点自定义器
     */
    @Resource
    private Optional<DeviceVerificationEndpointCustomizer> deviceVerificationEndpointCustomizer;
    /**
     * OpenID Connect自定义器
     */
    @Resource
    private Optional<OidcCustomizer> oidcCustomizer;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2AuthorizationServerConfigurer configurer) {
        // 定制OAuth2客户端认证
        clientAuthenticationCustomizer.ifPresent(configurer::clientAuthentication);
        // 定制授权服务器元数据端点
        authorizationServerMetadataEndpointCustomizer.ifPresent(configurer::authorizationServerMetadataEndpoint);
        // 定制授权端点
        authorizationEndpointCustomizer.ifPresent(configurer::authorizationEndpoint);
        // 定制令牌端点
        tokenEndpointCustomizer.ifPresent(configurer::tokenEndpoint);
        // 定制令牌验证端点
        tokenIntrospectionEndpointCustomizer.ifPresent(configurer::tokenIntrospectionEndpoint);
        // 定制令牌撤销端点
        tokenRevocationEndpointCustomizer.ifPresent(configurer::tokenRevocationEndpoint);
        // 定制设备授权端点
        deviceAuthorizationEndpointCustomizer.ifPresent(configurer::deviceAuthorizationEndpoint);
        // 定制设备验证端点
        deviceVerificationEndpointCustomizer.ifPresent(configurer::deviceVerificationEndpoint);
        // 定制OpenID Connect
        oidcCustomizer.ifPresent(configurer::oidc);
    }
}
