package com.gls.athena.starter.aliyun.oss;

import com.gls.athena.starter.aliyun.oss.config.AliyunOssProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({AliyunOssProperties.class})
public class AliyunOssAutoConfig {
}