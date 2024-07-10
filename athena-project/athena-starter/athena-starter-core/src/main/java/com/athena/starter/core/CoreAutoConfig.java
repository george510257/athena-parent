package com.athena.starter.core;

import com.athena.starter.core.async.ThreadPoolTaskProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 核心自动配置类
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({ThreadPoolTaskProperties.class})
public class CoreAutoConfig {
}