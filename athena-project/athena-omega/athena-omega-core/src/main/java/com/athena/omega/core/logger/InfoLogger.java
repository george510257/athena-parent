package com.athena.omega.core.logger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 信息日志
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class InfoLogger extends BaseLogger {

    @Override
    public String getName() {
        return "info";
    }
}
