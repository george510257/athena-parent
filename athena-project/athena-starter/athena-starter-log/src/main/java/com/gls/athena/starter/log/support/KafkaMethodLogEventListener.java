package com.gls.athena.starter.log.support;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.method.MethodLogEvent;
import com.gls.athena.starter.log.method.MethodLogEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaMethodLogEventListener implements MethodLogEventListener {
    /**
     * Kafka模板
     */
    private final KafkaTemplate<String, String> kafkaTemplate;
    /**
     * 主题
     */
    private final String topic;

    /**
     * 方法日志事件监听
     *
     * @param event 方法日志事件
     */
    @Override
    public void onApplicationEvent(MethodLogEvent event) {
        log.info("MethodLogEvent:{}", JSONUtil.toJsonStr(event));
        kafkaTemplate.send(topic, JSONUtil.toJsonStr(event));
    }
}
