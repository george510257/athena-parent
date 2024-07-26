package com.athena.security.resource.servlet.customizer;

import com.athena.security.resource.servlet.converter.JwtAuthenticationConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

/**
 * JWT自定义器
 *
 * @param jwtAuthenticationConverter JWT认证转换器
 */
@Component
public record JwtCustomizer(
        ObjectProvider<JwtAuthenticationConverter> jwtAuthenticationConverter)
        implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> {
    /**
     * 自定义
     *
     * @param jwtConfigurer 配置器
     */
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {
        // 配置jwt认证转换器
        jwtAuthenticationConverter.ifAvailable(jwtConfigurer::jwtAuthenticationConverter);
    }
}
