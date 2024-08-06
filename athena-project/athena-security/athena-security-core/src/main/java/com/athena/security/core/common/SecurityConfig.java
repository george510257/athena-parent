package com.athena.security.core.common;

import com.athena.security.core.jackson2.SecurityCoreModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.jackson2.WebServletJackson2Module;

/**
 * 安全配置
 */
@Configuration
@ComponentScan
public class SecurityConfig {

    /**
     * 密码编码器
     *
     * @return 密码编码器
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 认证管理器
     *
     * @param authenticationConfiguration 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public CoreJackson2Module coreJackson2Module() {
        return new CoreJackson2Module();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebServletJackson2Module webServletJackson2Module() {
        return new WebServletJackson2Module();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityCoreModule securityCoreModule() {
        return new SecurityCoreModule();
    }
}
