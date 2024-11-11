package com.gls.athena.security.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Servlet安全配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableWebSecurity
public class ServletSecurityAutoConfig {
}
