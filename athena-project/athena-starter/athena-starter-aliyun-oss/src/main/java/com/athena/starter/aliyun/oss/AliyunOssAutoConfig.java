package com.athena.starter.aliyun.oss;

import com.athena.starter.aliyun.oss.client.OssClientProperties;
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
@EnableConfigurationProperties({OssClientProperties.class})
public class AliyunOssAutoConfig {
}