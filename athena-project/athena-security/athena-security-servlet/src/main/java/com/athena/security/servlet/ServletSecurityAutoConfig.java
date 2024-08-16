package com.athena.security.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Servlet安全配置
 */
@Configuration
@ComponentScan
@EnableWebSecurity
public class ServletSecurityAutoConfig {
}
