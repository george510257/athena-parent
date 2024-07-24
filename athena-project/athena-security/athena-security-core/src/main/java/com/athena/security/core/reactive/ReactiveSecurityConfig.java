package com.athena.security.core.reactive;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.jackson2.WebServerJackson2Module;

@AutoConfiguration
@EnableWebFluxSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveSecurityConfig {

    @Bean
    @ConditionalOnMissingBean
    public WebServerJackson2Module webServerJackson2Module() {
        return new WebServerJackson2Module();
    }
}
