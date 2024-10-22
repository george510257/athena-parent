package com.athena.omega.core.logger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 调试日志
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DebugLogger extends BaseLogger {
    /**
     * 调试信息
     */
    private StackTraceElement[] trace;

    @Override
    public String getName() {
        return "debug";
    }
}
