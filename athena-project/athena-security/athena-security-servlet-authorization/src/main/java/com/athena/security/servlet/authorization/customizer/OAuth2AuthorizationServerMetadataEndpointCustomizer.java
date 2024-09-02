package com.athena.security.servlet.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerMetadataEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 授权服务器元数据端点自定义器
 *
 * @author george
 */
@Component
public class OAuth2AuthorizationServerMetadataEndpointCustomizer implements Customizer<OAuth2AuthorizationServerMetadataEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2AuthorizationServerMetadataEndpointConfigurer configurer) {
    }
}
