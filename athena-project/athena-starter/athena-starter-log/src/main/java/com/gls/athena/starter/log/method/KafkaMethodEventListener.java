package com.gls.athena.starter.log.method;

import com.gls.athena.starter.log.config.LogProperties;
import com.gls.athena.starter.log.domain.MethodEvent;
import com.gls.athena.starter.log.domain.MethodLogEvent;
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
public class KafkaMethodEventListener implements IMethodEventListener {
    /**
     * 日志配置
     */
    private final LogProperties logProperties;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Override
    public void onMethodEvent(MethodEvent event) {
        String key = getKafkaKey(event);
        log.info("发送方法日志: {}", event);
        kafkaTemplate.send(logProperties.getKafka().getTopic(), key, event);
    }


    /**
     * 获取kafka key
     *
     * @param event 方法事件
     * @return kafka key
     */
    private String getKafkaKey(MethodEvent event) {
        if (event instanceof MethodLogEvent) {
            return logProperties.getKafka().getMethodLogKey();
        }
        return logProperties.getKafka().getMethodKey();
    }

}
