package com.gls.athena.starter.log.config;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.method.MethodEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志配置
 *
 * @author george
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(LogProperties.class)
public class LogConfig {

    /**
     * 方法事件监听器
     *
     * @return MethodEventListener 方法事件监听器
     */
    @Bean
    @ConditionalOnMissingBean(MethodEventListener.class)
    public MethodEventListener methodEventListener() {
        return event -> log.info("MethodEvent: {}", JSONUtil.toJsonStr(event));
    }

}
