package com.athena.cloud.boot;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 云服务启动自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableDiscoveryClient
public class CloudBootAutoConfig {
}