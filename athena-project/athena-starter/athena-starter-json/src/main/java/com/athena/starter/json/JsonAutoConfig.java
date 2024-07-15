package com.athena.starter.json;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan
public class JsonAutoConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }
}