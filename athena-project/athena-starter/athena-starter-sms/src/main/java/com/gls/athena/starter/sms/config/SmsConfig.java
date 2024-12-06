package com.gls.athena.starter.sms.config;

import com.gls.athena.starter.sms.listener.KafkaSmsEventListener;
import com.gls.athena.starter.sms.listener.SmsEventListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 短信配置
 *
 * @author george
 */
@Slf4j
@AutoConfiguration
public class SmsConfig {
    /**
     * 短信事件监听器
     *
     * @return SmsEventListener 短信事件监听器
     */
    @Bean
    @ConditionalOnMissingBean(SmsEventListener.class)
    public SmsEventListener smsEventListener() {
        return event -> log.info("SmsEvent: {}", event);
    }

    /**
     * 短信 kafka 配置
     */
    @AutoConfiguration
    @ConditionalOnClass(KafkaTemplate.class)
    public static class SmsKafkaConfig {
        /**
         * kafka模板
         */
        @Resource
        private KafkaTemplate<String, String> kafkaTemplate;
        /**
         * 应用名称
         */
        @Value("${spring.application.name}")
        private String applicationName;
        /**
         * 短信配置
         */
        @Resource
        private SmsProperties smsProperties;

        /**
         * kafka短信事件监听器
         *
         * @return SmsEventListener kafka短信事件监听器
         */
        @Bean
        public SmsEventListener smsEventListener() {
            return new KafkaSmsEventListener(smsProperties, kafkaTemplate, applicationName);
        }
    }
}
