package com.gls.athena.starter.log.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 方法日志事件监听器
 *
 * @author george
 */
public interface MethodLogEventListener {
    /**
     * 方法日志事件监听
     *
     * @param event 方法日志事件
     */
    @Async
    @EventListener(MethodLogEvent.class)
    void onApplicationEvent(MethodLogEvent event);
}
