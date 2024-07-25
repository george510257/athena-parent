package com.athena.security.authorization.config;

import com.athena.security.authorization.customizer.OAuth2AuthorizationServerCustomizer;
import com.athena.security.core.servlet.customizer.AuthorizeHttpRequestsCustomizer;
import com.athena.security.core.servlet.customizer.CsrfCustomizer;
import com.athena.security.core.servlet.customizer.ExceptionHandlingCustomizer;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 授权配置
 */
@AutoConfiguration
public class AuthorizationConfig {
    /**
     * 授权服务器自定义器
     */
    @Resource
    private OAuth2AuthorizationServerCustomizer authorizationServerCustomizer;
    /**
     * 请求授权自定义器
     */
    @Resource
    private AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer;
    /**
     * 异常处理自定义器
     */
    @Resource
    private ExceptionHandlingCustomizer exceptionHandlingCustomizer;
    /**
     * CSRF自定义器
     */
    @Resource
    private CsrfCustomizer csrfCustomizer;

    /**
     * 授权安全过滤器链
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http) throws Exception {
        // OAuth2认证服务器配置器
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        return http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, authorizationServerCustomizer)
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer)
                .exceptionHandling(exceptionHandlingCustomizer)
                .csrf(csrfCustomizer)
                .build();
    }
}
