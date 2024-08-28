package com.athena.security.servlet.client.customizer;

import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.stereotype.Component;

/**
 * OAuth2 客户端自定义器
 */
@Component
public class OAuth2ClientCustomizer implements Customizer<OAuth2ClientConfigurer<HttpSecurity>> {
    /**
     * 授权码授权自定义器
     */
    @Resource
    private AuthorizationCodeGrantCustomizer authorizationCodeGrantCustomizer;

    /**
     * 自定义 OAuth2 客户端配置
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ClientConfigurer<HttpSecurity> configurer) {
        // 自定义 OAuth2 授权码授权配置
        configurer.authorizationCodeGrant(authorizationCodeGrantCustomizer);
    }
}
