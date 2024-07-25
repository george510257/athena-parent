package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerMetadataEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 授权服务器元数据端点自定义器
 */
@Component
public class AuthorizationServerMetadataEndpointCustomizer implements Customizer<OAuth2AuthorizationServerMetadataEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2AuthorizationServerMetadataEndpointConfigurer configurer) {
    }
}
