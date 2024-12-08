package com.gls.athena.starter.aliyun.sms;

import com.gls.athena.starter.aliyun.sms.config.SmsClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 短信自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(SmsClientProperties.class)
public class SmsAutoConfig {
}