package com.gls.athena.starter.aliyun.core;

import com.gls.athena.starter.aliyun.core.config.AliyunCoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云核心自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(AliyunCoreProperties.class)
public class AliyunCoreAutoConfig {
}
