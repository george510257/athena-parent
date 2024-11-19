package com.gls.athena.starter.log.method;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 方法日志消费者
 *
 * @author george
 */
public interface MethodLogListener {
    /**
     * 方法日志事件监听
     *
     * @param event 方法日志事件
     */
    @Async
    @EventListener(MethodLogEvent.class)
    void onMethodLogEvent(MethodLogEvent event);

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Async
    @EventListener(MethodEvent.class)
    void onMethodEvent(MethodEvent event);

}
