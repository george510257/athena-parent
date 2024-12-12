package com.gls.athena.starter.sms.listener;

import cn.hutool.core.bean.BeanUtil;
import com.gls.athena.starter.sms.config.SmsProperties;
import com.gls.athena.starter.sms.event.SmsEvent;
import com.gls.athena.starter.sms.support.SmsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 短信事件监听器
 *
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaSmsEventListener implements ISmsEventListener {
    /**
     * 短信配置
     */
    private final SmsProperties smsProperties;
    /**
     * kafka模板
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;
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
        SmsDto smsDto = covert(event);
        log.info("SmsEvent: {}", smsDto);
        String key = smsProperties.getKafka().getKey();
        String topic = smsProperties.getKafka().getTopic();
        kafkaTemplate.send(topic, key, smsDto);
    }

    /**
     * 转换
     *
     * @param event 短信事件
     * @return SmsDto 短信数据传输对象
     */
    private SmsDto covert(SmsEvent event) {
        SmsDto smsDto = new SmsDto();
        BeanUtil.copyProperties(event, smsDto);
        smsDto.setApplicationName(applicationName);
        return smsDto;
    }
}
