package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2DeviceAuthorizationEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 设备授权端点自定义器
 */
@Component
public class DeviceAuthorizationEndpointCustomizer implements Customizer<OAuth2DeviceAuthorizationEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2DeviceAuthorizationEndpointConfigurer configurer) {
    }
}
