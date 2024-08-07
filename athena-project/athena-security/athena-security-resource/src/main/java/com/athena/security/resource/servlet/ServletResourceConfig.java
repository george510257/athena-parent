package com.athena.security.resource.servlet;

import com.athena.security.core.servlet.customizer.AuthorizeHttpRequestsCustomizer;
import com.athena.security.core.servlet.customizer.CsrfCustomizer;
import com.athena.security.core.servlet.customizer.ExceptionHandlingCustomizer;
import com.athena.security.core.servlet.customizer.SessionManagementCustomizer;
import com.athena.security.resource.servlet.customizer.OAuth2ResourceServerCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@ComponentScan
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletResourceConfig {
    /**
     * 资源安全过滤器链
     *
     * @param http                            Http安全
     * @param oauth2ResourceServerCustomizer  OAuth2资源服务器自定义器
     * @param authorizeHttpRequestsCustomizer 请求授权自定义器
     * @param csrfCustomizer                  CSRF自定义器
     * @param sessionManagementCustomizer     会话管理自定义器
     * @param exceptionHandlingCustomizer     异常处理自定义器
     * @return 资源安全过滤器链
     * @throws Exception 异常
     */
    @Bean
    @Order(100)
    public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http,
                                                           OAuth2ResourceServerCustomizer oauth2ResourceServerCustomizer,
                                                           AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer,
                                                           CsrfCustomizer csrfCustomizer,
                                                           SessionManagementCustomizer sessionManagementCustomizer,
                                                           ExceptionHandlingCustomizer exceptionHandlingCustomizer) throws Exception {
        // 配置资源服务器
        http.oauth2ResourceServer(oauth2ResourceServerCustomizer);
        // 配置请求授权
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer);
        // 关闭csrf
        http.csrf(csrfCustomizer);
        // 配置session管理
        http.sessionManagement(sessionManagementCustomizer);
        // 配置异常处理
        http.exceptionHandling(exceptionHandlingCustomizer);
        return http.build();
    }
}
