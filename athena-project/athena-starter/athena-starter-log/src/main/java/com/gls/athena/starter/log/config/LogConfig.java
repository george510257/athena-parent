package com.gls.athena.starter.log.config;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.method.MethodEventListener;
import com.gls.athena.starter.log.support.KafkaMethodEventListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 日志配置
 *
 * @author george
 */
@Slf4j
@AutoConfiguration
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

    /**
     * 日志 kafka 配置
     *
     * @author george
     */

    @AutoConfiguration
    @ConditionalOnClass(KafkaTemplate.class)
    public static class LogKafkaConfig {
        /**
         * 应用名称
         */
        @Value("${spring.application.name}")
        private String applicationName;
        /**
         * 日志配置
         */
        @Resource
        private LogProperties logProperties;
        /**
         * kafka模板
         */
        @Resource
        private KafkaTemplate<String, String> kafkaTemplate;

        /**
         * kafka方法事件监听器
         *
         * @return MethodEventListener kafka方法事件监听器
         */
        @Bean
        public MethodEventListener kafkaMethodEventListener() {
            return new KafkaMethodEventListener(logProperties, kafkaTemplate, applicationName);
        }
    }
}
