package com.gls.athena.starter.log.support;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.config.LogProperties;
import com.gls.athena.starter.log.method.MethodEvent;
import com.gls.athena.starter.log.method.MethodEventListener;
import com.gls.athena.starter.log.method.MethodLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 方法日志消费者
 *
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaMethodEventListener implements MethodEventListener {
    /**
     * 日志配置
     */
    private final LogProperties logProperties;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Override
    public void onMethodEvent(MethodEvent event) {
        String message = JSONUtil.toJsonStr(event);
        LogProperties.Kafka kafka = logProperties.getKafka();
        log.info("MethodEvent: {}", message);
        String key = kafka.getMethodKey();
        if (event instanceof MethodLogEvent) {
            key = kafka.getMethodLogKey();
        }
        kafkaTemplate.send(kafka.getTopic(), key, message);
    }

}
