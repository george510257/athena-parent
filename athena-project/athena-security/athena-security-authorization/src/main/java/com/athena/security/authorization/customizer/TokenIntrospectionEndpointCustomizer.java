package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenIntrospectionEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 令牌验证端点自定义器
 */
@Component
public class TokenIntrospectionEndpointCustomizer implements Customizer<OAuth2TokenIntrospectionEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2TokenIntrospectionEndpointConfigurer configurer) {
    }
}
