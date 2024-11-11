package com.gls.athena.omega.core.logger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 成功日志
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SuccessLogger extends BaseLogger {
    @Override
    public String getName() {
        return "success";
    }
}
