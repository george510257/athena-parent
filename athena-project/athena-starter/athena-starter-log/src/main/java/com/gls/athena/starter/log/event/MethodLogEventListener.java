package com.gls.athena.starter.log.event;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 方法日志事件监听器
 *
 * @author george
 */
@Slf4j
@Component
public class MethodLogEventListener {
    /**
     * 方法日志事件监听
     *
     * @param event 方法日志事件
     */
    @Async
    @EventListener(MethodLogEvent.class)
    public void onApplicationEvent(MethodLogEvent event) {
        log.info("MethodLogEvent:{}", JSONUtil.toJsonStr(event));
    }
}
