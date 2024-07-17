package com.athena.starter.web;

import com.athena.starter.web.config.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Web自动配置
 */
@Configuration
@ComponentScan
@ServletComponentScan
@EnableConfigurationProperties({WebProperties.class})
public class WebAutoConfig {
}