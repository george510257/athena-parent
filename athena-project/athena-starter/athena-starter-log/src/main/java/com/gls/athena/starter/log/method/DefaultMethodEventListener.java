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
    private final LogProperties.Kafka kafka;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 构造方法
     *
     * @param kafka         日志配置
     * @param kafkaTemplate kafka模板
     */
    public DefaultMethodEventListener(LogProperties.Kafka kafka, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafka = kafka;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 构造方法
     *
     * @param kafka 日志配置
     */
    public DefaultMethodEventListener(LogProperties.Kafka kafka) {
        this(kafka, null);
    }

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Override
    public void onMethodEvent(MethodEvent event) {
        String message = JSONUtil.toJsonStr(event);
        log.info("MethodEvent: {}", message);
        if (kafka.isEnable() && kafkaTemplate != null) {
            String key = kafka.getMethodKey();
            if (event instanceof MethodLogEvent) {
                key = kafka.getMethodLogKey();
            }
            kafkaTemplate.send(kafka.getTopic(), key, message);
        }
    }

}
