package com.athena.security.core;

import com.athena.security.core.jackson2.CoreSecurityModule;
import com.athena.security.core.properties.CoreSecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(CoreSecurityProperties.class)
public class CoreSecurityAutoConfig {

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

    /**
     * 安全核心模块
     *
     * @return 安全核心模块
     */
    @Bean
    @ConditionalOnMissingBean
    public CoreJackson2Module coreJackson2Module() {
        return new CoreJackson2Module();
    }

    /**
     * Web安全模块
     *
     * @return Web安全模块
     */
    @Bean
    @ConditionalOnMissingBean
    public WebServletJackson2Module webServletJackson2Module() {
        return new WebServletJackson2Module();
    }

    /**
     * 安全核心模块
     *
     * @return 安全核心模块
     */
    @Bean
    @ConditionalOnMissingBean
    public CoreSecurityModule coreSecurityModule() {
        return new CoreSecurityModule();
    }
}
