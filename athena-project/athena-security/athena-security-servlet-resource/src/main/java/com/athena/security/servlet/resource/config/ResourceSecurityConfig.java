package com.athena.security.servlet.resource.config;

import com.athena.security.servlet.customizer.AuthorizeHttpRequestsCustomizer;
import com.athena.security.servlet.customizer.CsrfCustomizer;
import com.athena.security.servlet.resource.customizer.OAuth2ResourceServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源安全配置
 *
 * @author george
 */
@Configuration
public class ResourceSecurityConfig {

    /**
     * 资源安全过滤器链
     *
     * @param http                            Http安全
     * @param oauth2ResourceServerCustomizer  OAuth2资源服务器自定义器
     * @param authorizeHttpRequestsCustomizer 请求授权自定义器
     * @param csrfCustomizer                  CSRF自定义器
     * @return SecurityFilterChain 安全过滤器链
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http,
                                                           OAuth2ResourceServerCustomizer oauth2ResourceServerCustomizer,
                                                           AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer,
                                                           CsrfCustomizer csrfCustomizer) throws Exception {
        // 资源服务器
        http.oauth2ResourceServer(oauth2ResourceServerCustomizer);
        // 配置请求授权
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer);
        // CSRF
        http.csrf(csrfCustomizer);
        // 构建
        return http.build();
    }
}
