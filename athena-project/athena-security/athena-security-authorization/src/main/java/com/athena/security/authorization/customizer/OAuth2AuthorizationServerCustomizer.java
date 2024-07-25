package com.athena.security.authorization.customizer;

import jakarta.annotation.Resource;
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
    private ClientAuthenticationCustomizer clientAuthenticationCustomizer;
    /**
     * 授权服务器元数据端点自定义器
     */
    @Resource
    private AuthorizationServerMetadataEndpointCustomizer authorizationServerMetadataEndpointCustomizer;
    /**
     * 授权端点自定义器
     */
    @Resource
    private AuthorizationEndpointCustomizer authorizationEndpointCustomizer;
    /**
     * 令牌端点自定义器
     */
    @Resource
    private TokenEndpointCustomizer tokenEndpointCustomizer;
    /**
     * 令牌验证端点自定义器
     */
    @Resource
    private TokenIntrospectionEndpointCustomizer tokenIntrospectionEndpointCustomizer;
    /**
     * 令牌撤销端点自定义器
     */
    @Resource
    private TokenRevocationEndpointCustomizer tokenRevocationEndpointCustomizer;
    /**
     * 设备授权端点自定义器
     */
    @Resource
    private DeviceAuthorizationEndpointCustomizer deviceAuthorizationEndpointCustomizer;
    /**
     * 设备验证端点自定义器
     */
    @Resource
    private DeviceVerificationEndpointCustomizer deviceVerificationEndpointCustomizer;
    /**
     * OpenID Connect自定义器
     */
    @Resource
    private OidcCustomizer oidcCustomizer;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2AuthorizationServerConfigurer configurer) {
        // 定制OAuth2客户端认证
        configurer.clientAuthentication(clientAuthenticationCustomizer);
        // 定制授权服务器元数据端点
        configurer.authorizationServerMetadataEndpoint(authorizationServerMetadataEndpointCustomizer);
        // 定制授权端点
        configurer.authorizationEndpoint(authorizationEndpointCustomizer);
        // 定制令牌端点
        configurer.tokenEndpoint(tokenEndpointCustomizer);
        // 定制令牌验证端点
        configurer.tokenIntrospectionEndpoint(tokenIntrospectionEndpointCustomizer);
        // 定制令牌撤销端点
        configurer.tokenRevocationEndpoint(tokenRevocationEndpointCustomizer);
        // 定制设备授权端点
        configurer.deviceAuthorizationEndpoint(deviceAuthorizationEndpointCustomizer);
        // 定制设备验证端点
        configurer.deviceVerificationEndpoint(deviceVerificationEndpointCustomizer);
        // 定制OpenID Connect
        configurer.oidc(oidcCustomizer);
    }
}
