package com.gls.athena.starter.sms.sender;

import cn.hutool.extra.spring.SpringUtil;
import com.gls.athena.starter.sms.event.SmsEvent;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * 短信发送器
 *
 * @author george
 */
@UtilityClass
public class SmsSender {
    /**
     * 发送短信
     *
     * @param phone        手机号
     * @param templateCode 模板编号
     * @param params       参数
     */
    public void send(Object source, String phone, String templateCode, Map<String, Object> params) {
        SmsEvent smsEvent = new SmsEvent(source, phone, templateCode, params);
        SpringUtil.publishEvent(smsEvent);
    }
}
