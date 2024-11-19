package com.gls.athena.starter.log.method;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.config.LogProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 方法日志消费者
 *
 * @author george
 */
@Slf4j
public class DefaultMethodLogListener implements MethodLogListener {
    /**
     * 日志配置
     */
    private final LogProperties logProperties;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 构造方法
     *
     * @param logProperties 日志配置
     * @param kafkaTemplate kafka模板
     */
    public DefaultMethodLogListener(LogProperties logProperties, KafkaTemplate<String, String> kafkaTemplate) {
        this.logProperties = logProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 构造方法
     *
     * @param logProperties 日志配置
     */
    public DefaultMethodLogListener(LogProperties logProperties) {
        this(logProperties, null);
    }

    @Override
    public void onMethodLogEvent(MethodLogEvent event) {
        log.info("MethodLogEvent: {}", JSONUtil.toJsonStr(event));
        if (logProperties.isKafkaEnable() && kafkaTemplate != null) {
            kafkaTemplate.send(logProperties.getKafkaTopic(), logProperties.getKafkaAddLogKey(), JSONUtil.toJsonStr(event));
            log.info("MethodLogEvent send to kafka: {}", JSONUtil.toJsonStr(event));
        }
    }

    @Override
    public void onMethodEvent(MethodEvent event) {
        log.info("MethodEvent: {}", JSONUtil.toJsonStr(event));
        if (logProperties.isKafkaEnable() && kafkaTemplate != null) {
            kafkaTemplate.send(logProperties.getKafkaTopic(), logProperties.getKafkaAddMethodKey(), JSONUtil.toJsonStr(event));
            log.info("MethodEvent send to kafka: {}", JSONUtil.toJsonStr(event));
        }
    }

}
