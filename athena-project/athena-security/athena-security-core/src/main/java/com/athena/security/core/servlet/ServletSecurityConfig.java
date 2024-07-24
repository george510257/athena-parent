package com.athena.security.core.servlet;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.jackson2.WebServletJackson2Module;

@AutoConfiguration
@EnableWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletSecurityConfig {

    @Bean
    @ConditionalOnMissingBean
    public WebServletJackson2Module webServletJackson2Module() {
        return new WebServletJackson2Module();
    }
}
