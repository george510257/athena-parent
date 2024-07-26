package com.athena.security.authorization.customizer;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.stereotype.Component;

/**
 * OAuth2授权服务器自定义器
 */
@Component
public class OAuth2AuthorizationServerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    /**
     * 客户端认证自定义器
     */
    @Resource
    private ObjectProvider<ClientAuthenticationCustomizer> clientAuthenticationCustomizer;
    /**
     * 授权服务器元数据端点自定义器
     */
    @Resource
    private ObjectProvider<AuthorizationServerMetadataEndpointCustomizer> authorizationServerMetadataEndpointCustomizer;
    /**
     * 授权端点自定义器
     */
    @Resource
    private ObjectProvider<AuthorizationEndpointCustomizer> authorizationEndpointCustomizer;
    /**
     * 令牌端点自定义器
     */
    @Resource
    private ObjectProvider<TokenEndpointCustomizer> tokenEndpointCustomizer;
    /**
     * 令牌验证端点自定义器
     */
    @Resource
    private ObjectProvider<TokenIntrospectionEndpointCustomizer> tokenIntrospectionEndpointCustomizer;
    /**
     * 令牌撤销端点自定义器
     */
    @Resource
    private ObjectProvider<TokenRevocationEndpointCustomizer> tokenRevocationEndpointCustomizer;
    /**
     * 设备授权端点自定义器
     */
    @Resource
    private ObjectProvider<DeviceAuthorizationEndpointCustomizer> deviceAuthorizationEndpointCustomizer;
    /**
     * 设备验证端点自定义器
     */
    @Resource
    private ObjectProvider<DeviceVerificationEndpointCustomizer> deviceVerificationEndpointCustomizer;
    /**
     * OpenID Connect自定义器
     */
    @Resource
    private ObjectProvider<OidcCustomizer> oidcCustomizer;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2AuthorizationServerConfigurer configurer) {
        // 定制OAuth2客户端认证
        clientAuthenticationCustomizer.ifAvailable(configurer::clientAuthentication);
        // 定制授权服务器元数据端点
        authorizationServerMetadataEndpointCustomizer.ifAvailable(configurer::authorizationServerMetadataEndpoint);
        // 定制授权端点
        authorizationEndpointCustomizer.ifAvailable(configurer::authorizationEndpoint);
        // 定制令牌端点
        tokenEndpointCustomizer.ifAvailable(configurer::tokenEndpoint);
        // 定制令牌验证端点
        tokenIntrospectionEndpointCustomizer.ifAvailable(configurer::tokenIntrospectionEndpoint);
        // 定制令牌撤销端点
        tokenRevocationEndpointCustomizer.ifAvailable(configurer::tokenRevocationEndpoint);
        // 定制设备授权端点
        deviceAuthorizationEndpointCustomizer.ifAvailable(configurer::deviceAuthorizationEndpoint);
        // 定制设备验证端点
        deviceVerificationEndpointCustomizer.ifAvailable(configurer::deviceVerificationEndpoint);
        // 定制OpenID Connect
        oidcCustomizer.ifAvailable(configurer::oidc);
    }
}
