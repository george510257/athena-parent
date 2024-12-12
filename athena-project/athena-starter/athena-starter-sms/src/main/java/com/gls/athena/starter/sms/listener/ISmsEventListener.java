package com.gls.athena.starter.sms.listener;

import com.gls.athena.starter.sms.event.SmsEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 短信事件消费者
 *
 * @author george
 */
public interface ISmsEventListener {

    /**
     * 短信事件监听
     *
     * @param event 短信事件
     */
    @Async
    @EventListener(SmsEvent.class)
    void onSmsEvent(SmsEvent event);
}
