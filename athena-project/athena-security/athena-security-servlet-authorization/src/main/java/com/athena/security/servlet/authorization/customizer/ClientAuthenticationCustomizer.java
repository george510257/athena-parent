package com.athena.security.servlet.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2ClientAuthenticationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 客户端认证自定义器
 */
@Component
public class ClientAuthenticationCustomizer implements Customizer<OAuth2ClientAuthenticationConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ClientAuthenticationConfigurer configurer) {
    }
}
