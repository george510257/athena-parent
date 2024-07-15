package com.athena.starter.mybatis;

import com.athena.starter.mybatis.config.MybatisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisAutoConfig {
}