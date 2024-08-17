package com.athena.security.reactive;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@Configuration
@ComponentScan
@EnableWebFluxSecurity
public class ReactiveSecurityAutoConfig {
}
