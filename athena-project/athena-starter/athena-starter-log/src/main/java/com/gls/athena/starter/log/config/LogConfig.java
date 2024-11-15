package com.gls.athena.starter.log.config;

import com.gls.athena.starter.log.method.MethodLogEventListener;
import com.gls.athena.starter.log.support.DefaultMethodLogEventListener;
import com.gls.athena.starter.log.support.KafkaMethodLogEventListener;
import jakarta.annotation.Resource;
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
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(LogProperties.class)
public class LogConfig {
    @Resource
    private LogProperties logProperties;
    @Resource
    private ObjectProvider<KafkaTemplate<String, String>> kafkaTemplateObjectProvider;

    /**
     * 方法日志事件监听器
     *
     * @return 方法日志事件监听器
     */
    @Bean
    @ConditionalOnMissingBean
    public MethodLogEventListener methodLogEventListener() {
        if (logProperties.isKafkaEnable() && kafkaTemplateObjectProvider.getIfAvailable() != null) {
            return new KafkaMethodLogEventListener(kafkaTemplateObjectProvider.getIfAvailable(), logProperties.getKafkaTopic());
        }
        return new DefaultMethodLogEventListener();
    }
}
