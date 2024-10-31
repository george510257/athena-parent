package com.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Action数据注册器
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionDataRegistry implements Serializable {
    /**
     * 数据
     */
    @Delegate
    private final Map<String, Object> data = new HashMap<>();
}
