package com.gls.athena.starter.log.support;

import cn.hutool.json.JSONUtil;
import com.gls.athena.starter.log.method.MethodLogEvent;
import com.gls.athena.starter.log.method.MethodLogEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author george
 */
@Slf4j
public class DefaultMethodLogEventListener implements MethodLogEventListener {

    /**
     * 方法日志事件监听
     *
     * @param event 方法日志事件
     */
    @Override
    public void onApplicationEvent(MethodLogEvent event) {
        log.info("MethodLogEvent:{}", JSONUtil.toJsonStr(event));
    }
}
