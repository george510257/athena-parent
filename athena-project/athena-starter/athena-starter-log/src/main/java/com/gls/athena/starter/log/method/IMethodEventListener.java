package com.gls.athena.starter.log.method;

import com.gls.athena.starter.log.domain.MethodEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 方法日志消费者
 *
 * @author george
 */
public interface IMethodEventListener {

    /**
     * 方法事件监听
     *
     * @param event 方法事件
     */
    @Async
    @EventListener(MethodEvent.class)
    void onMethodEvent(MethodEvent event);

}
