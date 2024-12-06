package com.gls.athena.starter.sms;

import com.gls.athena.starter.sms.config.SmsProperties;
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
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfig {
}