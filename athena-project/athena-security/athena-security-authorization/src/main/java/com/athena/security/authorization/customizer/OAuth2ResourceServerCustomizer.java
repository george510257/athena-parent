package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

@Component
public class OAuth2ResourceServerCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        configurer.jwt(Customizer.withDefaults());
    }
}
