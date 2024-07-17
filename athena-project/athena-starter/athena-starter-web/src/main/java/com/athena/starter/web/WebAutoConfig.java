package com.athena.starter.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Web自动配置
 */
@Configuration
@ComponentScan
@ServletComponentScan
public class WebAutoConfig {
}