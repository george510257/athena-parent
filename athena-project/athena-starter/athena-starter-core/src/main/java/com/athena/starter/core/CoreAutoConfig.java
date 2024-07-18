package com.athena.starter.core;

import com.athena.common.bean.security.IUserHelper;
import com.athena.starter.core.async.ThreadPoolTaskProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 核心自动配置类
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({ThreadPoolTaskProperties.class})
public class CoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public IUserHelper userHelper() {
        return IUserHelper.withDefaults();
    }
}