package com.gls.athena.starter.log.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.config.LogProperties;
import com.gls.athena.starter.log.method.MethodEvent;
import com.gls.athena.starter.log.method.MethodEventListener;
import com.gls.athena.starter.log.method.MethodLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

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
     * 应用名称
     */
    private final String applicationName;

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Override
    public void onMethodEvent(MethodEvent event) {
        Map<String, Object> message = BeanUtil.beanToMap(event);
        message.put("applicationName", applicationName);
        LogProperties.Kafka kafka = logProperties.getKafka();
        log.info("MethodEvent: {}", message);
        String key = kafka.getMethodKey();
        if (event instanceof MethodLogEvent) {
            key = kafka.getMethodLogKey();
        }
        kafkaTemplate.send(kafka.getTopic(), key, JSONUtil.toJsonStr(message));
    }

}
