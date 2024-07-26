package com.athena.security.authorization.config;

import com.athena.security.authorization.customizer.OAuth2AuthorizationServerCustomizer;
import com.athena.security.core.servlet.customizer.AuthorizeHttpRequestsCustomizer;
import com.athena.security.core.servlet.customizer.CsrfCustomizer;
import com.athena.security.core.servlet.customizer.ExceptionHandlingCustomizer;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
     * 授权安全过滤器链
     *
     * @param http                            Http安全
     * @param authorizationServerCustomizer   授权服务器自定义器
     * @param authorizeHttpRequestsCustomizer 请求授权自定义器
     * @param exceptionHandlingCustomizer     异常处理自定义器
     * @param csrfCustomizer                  CSRF自定义器
     * @return 授权安全过滤器链
     */
    @Bean
    @SneakyThrows
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnBean({HttpSecurity.class, OAuth2AuthorizationServerCustomizer.class})
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http,
                                                                OAuth2AuthorizationServerCustomizer authorizationServerCustomizer,
                                                                AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer,
                                                                ExceptionHandlingCustomizer exceptionHandlingCustomizer,
                                                                CsrfCustomizer csrfCustomizer) {
        // OAuth2认证服务器配置器
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, authorizationServerCustomizer);
        // 自定义授权请求
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer);
        // 自定义异常处理
        http.exceptionHandling(exceptionHandlingCustomizer);
        // 自定义CSRF
        http.csrf(csrfCustomizer);
        return http.build();
    }
}
