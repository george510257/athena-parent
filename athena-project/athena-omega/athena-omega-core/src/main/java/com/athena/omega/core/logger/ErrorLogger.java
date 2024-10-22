package com.athena.omega.core.logger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 错误日志
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ErrorLogger extends BaseLogger {
    /**
     * 异常
     */
    private Throwable throwable;

    @Override
    public String getName() {
        return "error";
    }
}
