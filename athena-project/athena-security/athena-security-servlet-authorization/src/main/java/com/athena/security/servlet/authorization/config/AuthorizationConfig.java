package com.athena.security.servlet.authorization.config;

import com.athena.security.servlet.authorization.customizer.OAuth2AuthorizationServerCustomizer;
import com.athena.security.servlet.authorization.customizer.OAuth2ResourceServerCustomizer;
import com.athena.security.servlet.customizer.AuthorizeHttpRequestsCustomizer;
import com.athena.security.servlet.customizer.CsrfCustomizer;
import com.athena.security.servlet.customizer.ExceptionHandlingCustomizer;
import com.athena.security.servlet.customizer.RestCustomizer;
import com.athena.security.servlet.rest.RestConfigurer;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 授权配置
 */
@Configuration
public class AuthorizationConfig {

    /**
     * 授权安全过滤器链
     *
     * @param http                          Http安全
     * @param authorizationServerCustomizer 授权服务器自定义器
     * @param resourceServerCustomizer      资源服务器自定义器
     * @param exceptionHandlingCustomizer   异常处理自定义器
     * @return 授权安全过滤器链
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http,
                                                                OAuth2AuthorizationServerCustomizer authorizationServerCustomizer,
                                                                OAuth2ResourceServerCustomizer resourceServerCustomizer,
                                                                ExceptionHandlingCustomizer exceptionHandlingCustomizer) throws Exception {
        // 默认安全配置
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        // 自定义安全配置
        authorizationServerCustomizer.customize(http.getConfigurer(OAuth2AuthorizationServerConfigurer.class));
        // 资源服务器
        http.oauth2ResourceServer(resourceServerCustomizer);
        // 异常处理
        http.exceptionHandling(exceptionHandlingCustomizer);
        // 构建
        return http.build();
    }

    /**
     * 默认安全配置
     *
     * @param http                            Http安全
     * @param restCustomizer                  REST自定义器
     * @param authorizeHttpRequestsCustomizer 请求授权自定义器
     * @param csrfCustomizer                  CSRF自定义器
     */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                   RestCustomizer restCustomizer,
                                                   AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer,
                                                   CsrfCustomizer csrfCustomizer) throws Exception {
        http.with(new RestConfigurer<>(), restCustomizer);
        // 配置请求授权
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer);
        // CSRF
        http.csrf(csrfCustomizer);
        // 构建
        return http.build();
    }
}
