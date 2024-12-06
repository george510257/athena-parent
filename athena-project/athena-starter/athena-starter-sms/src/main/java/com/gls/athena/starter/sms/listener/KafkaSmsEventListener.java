package com.gls.athena.starter.sms.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.sms.config.SmsProperties;
import com.gls.athena.starter.sms.event.SmsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

/**
 * 短信事件监听器
 *
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaSmsEventListener implements SmsEventListener {
    /**
     * 短信配置
     */
    private final SmsProperties smsProperties;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, String> kafkaTemplate;
    /**
     * 应用名称
     */
    private final String applicationName;

    /**
     * 短信事件监听
     *
     * @param event 短信事件
     */
    @Override
    public void onSmsEvent(SmsEvent event) {
        log.info("SmsEvent: {}", event);
        Map<String, Object> message = BeanUtil.beanToMap(event);
        message.put("applicationName", applicationName);
        String key = smsProperties.getKafka().getKey();
        String topic = smsProperties.getKafka().getTopic();
        kafkaTemplate.send(topic, key, JSONUtil.toJsonStr(message));
    }
}
