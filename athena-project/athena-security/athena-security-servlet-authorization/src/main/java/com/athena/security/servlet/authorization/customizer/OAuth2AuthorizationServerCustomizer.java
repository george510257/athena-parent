package com.athena.security.servlet.authorization.customizer;

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
    private Optional<OAuth2ClientAuthenticationCustomizer> oauth2ClientAuthenticationCustomizer;
    /**
     * 授权服务器元数据端点自定义器
     */
    @Resource
    private Optional<OAuth2AuthorizationServerMetadataEndpointCustomizer> oauth2AuthorizationServerMetadataEndpointCustomizer;
    /**
     * 授权端点自定义器
     */
    @Resource
    private Optional<OAuth2AuthorizationEndpointCustomizer> oauth2AuthorizationEndpointCustomizer;
    /**
     * 令牌端点自定义器
     */
    @Resource
    private Optional<OAuth2TokenEndpointCustomizer> oauth2TokenEndpointCustomizer;
    /**
     * 令牌验证端点自定义器
     */
    @Resource
    private Optional<OAuth2TokenIntrospectionEndpointCustomizer> oauth2TokenIntrospectionEndpointCustomizer;
    /**
     * 令牌撤销端点自定义器
     */
    @Resource
    private Optional<OAuth2TokenRevocationEndpointCustomizer> oauth2TokenRevocationEndpointCustomizer;
    /**
     * 设备授权端点自定义器
     */
    @Resource
    private Optional<OAuth2DeviceAuthorizationEndpointCustomizer> oauth2DeviceAuthorizationEndpointCustomizer;
    /**
     * 设备验证端点自定义器
     */
    @Resource
    private Optional<OAuth2DeviceVerificationEndpointCustomizer> oauth2DeviceVerificationEndpointCustomizer;
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
        oauth2ClientAuthenticationCustomizer.ifPresent(configurer::clientAuthentication);
        // 定制授权服务器元数据端点
        oauth2AuthorizationServerMetadataEndpointCustomizer.ifPresent(configurer::authorizationServerMetadataEndpoint);
        // 定制授权端点
        oauth2AuthorizationEndpointCustomizer.ifPresent(configurer::authorizationEndpoint);
        // 定制令牌端点
        oauth2TokenEndpointCustomizer.ifPresent(configurer::tokenEndpoint);
        // 定制令牌验证端点
        oauth2TokenIntrospectionEndpointCustomizer.ifPresent(configurer::tokenIntrospectionEndpoint);
        // 定制令牌撤销端点
        oauth2TokenRevocationEndpointCustomizer.ifPresent(configurer::tokenRevocationEndpoint);
        // 定制设备授权端点
        oauth2DeviceAuthorizationEndpointCustomizer.ifPresent(configurer::deviceAuthorizationEndpoint);
        // 定制设备验证端点
        oauth2DeviceVerificationEndpointCustomizer.ifPresent(configurer::deviceVerificationEndpoint);
        // 定制OpenID Connect
        oidcCustomizer.ifPresent(configurer::oidc);
    }
}
