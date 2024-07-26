package com.athena.security.resource.customizer.servlet;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

@Component
public class JwtCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> {

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {

    }
}
