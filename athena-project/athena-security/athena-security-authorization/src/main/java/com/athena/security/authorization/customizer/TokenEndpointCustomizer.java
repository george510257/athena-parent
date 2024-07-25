package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 令牌端点自定义器
 */
@Component
public class TokenEndpointCustomizer implements Customizer<OAuth2TokenEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2TokenEndpointConfigurer configurer) {
    }
}
