package com.gls.athena.starter.log.config;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.method.MethodLogEventListener;
import jakarta.annotation.Resource;
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
     * 日志配置
     */
    @Resource
    private LogProperties logProperties;

    /**
     * 方法日志事件监听器
     *
     * @param kafkaTemplate kafka模板
     * @return 方法日志事件监听器
     */
    @Bean
    @ConditionalOnMissingBean
    public MethodLogEventListener methodLogEventListener(ObjectProvider<KafkaTemplate<String, String>> kafkaTemplate) {
        return event -> {
            log.info("MethodLogEvent: {}", JSONUtil.toJsonStr(event));
            kafkaTemplate.ifAvailable(template -> {
                if (logProperties.isKafkaEnable()) {
                    template.send(logProperties.getKafkaTopic(), JSONUtil.toJsonStr(event));
                    log.info("MethodLogEvent send to kafka: {}", JSONUtil.toJsonStr(event));
                }
            });
        };
    }
}
