package com.athena.starter.mybatis;

import com.athena.common.core.constant.BaseConstants;
import com.athena.starter.mybatis.config.MybatisProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@MapperScan(basePackages = BaseConstants.BASE_PACKAGE_PREFIX + ".**.mapper")
@EnableConfigurationProperties({MybatisProperties.class})
public class MybatisAutoConfig {
}