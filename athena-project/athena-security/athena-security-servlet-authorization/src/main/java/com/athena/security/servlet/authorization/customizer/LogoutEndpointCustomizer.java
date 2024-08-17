package com.athena.security.servlet.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcLogoutEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 登出端点自定义器
 */
@Component
public class LogoutEndpointCustomizer implements Customizer<OidcLogoutEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcLogoutEndpointConfigurer configurer) {
    }
}
