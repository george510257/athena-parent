package com.athena.starter.swagger;

import com.athena.starter.swagger.config.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerAutoConfig {
}