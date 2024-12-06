package com.gls.athena.starter.log.config;

import com.gls.athena.starter.log.method.MethodEventListener;
import com.gls.athena.starter.log.support.KafkaMethodEventListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 日志 kafka 配置
 *
 * @author george
 */
@Configuration
@ConditionalOnClass(KafkaTemplate.class)
public class LogKafkaConfig {
    /**
     * kafka方法事件监听器
     *
     * @param logProperties 日志配置
     * @param kafkaTemplate kafka模板
     * @return KafkaMethodEventListener kafka方法事件监听器
     */
    @Bean
    @ConditionalOnBean(KafkaTemplate.class)
    public MethodEventListener kafkaMethodEventListener(LogProperties logProperties, KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaMethodEventListener(logProperties, kafkaTemplate);
    }
}
