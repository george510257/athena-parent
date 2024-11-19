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
public class DefaultMethodEventListener implements MethodEventListener {
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
    public DefaultMethodEventListener(LogProperties logProperties, KafkaTemplate<String, String> kafkaTemplate) {
        this.logProperties = logProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 构造方法
     *
     * @param logProperties 日志配置
     */
    public DefaultMethodEventListener(LogProperties logProperties) {
        this(logProperties, null);
    }

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Override
    public void onMethodEvent(MethodEvent event) {
        log.info("MethodEvent: {}", JSONUtil.toJsonStr(event));
        if (logProperties.isKafkaEnable() && kafkaTemplate != null) {
            String key = logProperties.getKafkaAddMethodKey();
            if (event instanceof MethodLogEvent) {
                key = logProperties.getKafkaAddLogKey();
            }
            kafkaTemplate.send(logProperties.getKafkaTopic(), key, JSONUtil.toJsonStr(event));
            log.info("MethodEvent send to kafka: {}", JSONUtil.toJsonStr(event));
        }
    }

}
