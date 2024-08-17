package com.athena.security.servlet.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcClientRegistrationEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 客户端注册端点自定义器
 */
@Component
public class ClientRegistrationEndpointCustomizer implements Customizer<OidcClientRegistrationEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcClientRegistrationEndpointConfigurer configurer) {
    }
}
