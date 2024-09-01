package com.athena.security.servlet.client.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.stereotype.Component;

/**
 * 授权码授权自定义器
 *
 * @author george
 */
@Component
public class AuthorizationCodeGrantCustomizer implements Customizer<OAuth2ClientConfigurer<HttpSecurity>.AuthorizationCodeGrantConfigurer> {
    /**
     * 自定义 OAuth2 授权码授权配置
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ClientConfigurer<HttpSecurity>.AuthorizationCodeGrantConfigurer configurer) {
    }
}
