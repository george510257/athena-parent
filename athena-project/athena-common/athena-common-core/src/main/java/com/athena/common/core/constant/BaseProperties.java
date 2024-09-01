package com.athena.common.core.constant;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 基础属性
 *
 * @author george
 */
@Data
public abstract class BaseProperties implements Serializable {
    /**
     * 是否生效
     */
    private boolean enabled = true;
    /**
     * 扩展属性
     */
    private Map<String, Object> extensions;
}
