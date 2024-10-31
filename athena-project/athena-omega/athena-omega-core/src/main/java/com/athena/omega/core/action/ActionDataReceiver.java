package com.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Map;

/**
 * Action数据接收器
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionDataReceiver {
    /**
     * 请求参数
     */
    @Delegate
    private final Map<String, ActionParam<?>> params = new HashMap<>();

}
