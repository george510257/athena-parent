package com.athena.security.servlet.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcProviderConfigurationEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 提供者配置端点自定义器
 */
@Component
public class ProviderConfigurationEndpointCustomizer implements Customizer<OidcProviderConfigurationEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcProviderConfigurationEndpointConfigurer configurer) {
    }
}
