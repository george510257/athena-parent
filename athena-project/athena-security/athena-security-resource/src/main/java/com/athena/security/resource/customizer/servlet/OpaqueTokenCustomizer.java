package com.athena.security.resource.customizer.servlet;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

/**
 * 不透明令牌自定义器
 */
@Component
public class OpaqueTokenCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.OpaqueTokenConfigurer> {
    /**
     * 自定义
     *
     * @param opaqueTokenConfigurer 配置器
     */
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity>.OpaqueTokenConfigurer opaqueTokenConfigurer) {

    }
}
