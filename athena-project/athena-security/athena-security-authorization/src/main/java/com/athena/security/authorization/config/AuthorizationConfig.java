package com.athena.security.authorization.config;

import com.athena.security.authorization.customizer.OAuth2AuthorizationServerCustomizer;
import com.athena.security.authorization.customizer.OAuth2ResourceServerCustomizer;
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
     * @return 授权安全过滤器链
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http,
                                                                OAuth2AuthorizationServerCustomizer authorizationServerCustomizer,
                                                                OAuth2ResourceServerCustomizer resourceServerCustomizer) throws Exception {
        // 默认安全配置
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        // 自定义安全配置
        authorizationServerCustomizer.customize(http.getConfigurer(OAuth2AuthorizationServerConfigurer.class));
        // 资源服务器
        http.oauth2ResourceServer(resourceServerCustomizer);
        // 构建
        return http.build();
    }
}
