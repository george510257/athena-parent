package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2DeviceVerificationEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 设备验证端点自定义器
 */
@Component
public class DeviceVerificationEndpointCustomizer implements Customizer<OAuth2DeviceVerificationEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2DeviceVerificationEndpointConfigurer configurer) {
    }
}
