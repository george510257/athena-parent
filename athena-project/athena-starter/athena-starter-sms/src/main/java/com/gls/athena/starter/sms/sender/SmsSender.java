package com.gls.athena.starter.sms.sender;

import com.gls.athena.starter.sms.event.SmsEvent;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信发送器
 *
 * @author george
 */
@Component
public class SmsSender {
    /**
     * 事件发布器
     */
    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 发送短信
     *
     * @param phone        手机号
     * @param templateCode 模板编号
     * @param params       参数
     */
    public void send(String phone, String templateCode, Map<String, Object> params) {
        SmsEvent smsEvent = new SmsEvent(this, phone, templateCode, params);
        publisher.publishEvent(smsEvent);
    }
}
