package com.athena.upms.sdk;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Athena UPMS Sdk自动配置
 */
@Configuration
@ComponentScan
@EnableFeignClients
public class UpmsSdkAutoConfig {
}