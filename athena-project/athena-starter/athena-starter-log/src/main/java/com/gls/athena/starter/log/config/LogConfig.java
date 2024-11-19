package com.gls.athena.starter.log.config;

import com.gls.athena.starter.log.method.DefaultMethodEventListener;
import com.gls.athena.starter.log.method.MethodEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;

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
     * 方法日志消费者
     *
     * @param logProperties 日志配置
     * @param kafkaTemplate kafka模板
     * @return 方法日志消费者
     */
    @Bean
    @ConditionalOnMissingBean
    public MethodEventListener methodLogConsumer(LogProperties logProperties, ObjectProvider<KafkaTemplate<String, String>> kafkaTemplate) {
        if (logProperties.isKafkaEnable()) {
            return new DefaultMethodEventListener(logProperties, kafkaTemplate.getIfAvailable());
        }
        return new DefaultMethodEventListener(logProperties);
    }
}
