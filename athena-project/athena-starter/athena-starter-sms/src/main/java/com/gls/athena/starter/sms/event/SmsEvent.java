package com.gls.athena.starter.sms.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 短信事件
 *
 * @author george
 */
@Getter
public class SmsEvent extends ApplicationEvent {
    /**
     * 手机号
     */
    private final String mobile;
    /**
     * 模板编码
     */
    private final String templateCode;
    /**
     * 参数
     */
    private final Map<String, Object> params;

    /**
     * 构造方法
     *
     * @param source       来源
     * @param mobile       手机号
     * @param templateCode 模板编码
     * @param params       参数
     */
    public SmsEvent(Object source, String mobile, String templateCode, Map<String, Object> params) {
        super(source);
        this.mobile = mobile;
        this.templateCode = templateCode;
        this.params = params;
    }
}
