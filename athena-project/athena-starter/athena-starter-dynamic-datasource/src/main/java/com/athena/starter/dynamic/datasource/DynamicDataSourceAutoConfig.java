package com.athena.starter.dynamic.datasource;

import com.athena.starter.dynamic.datasource.support.DynamicDataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 动态数据源自动配置
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({DynamicDataSourceProperties.class})
public class DynamicDataSourceAutoConfig {
}