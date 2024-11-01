package com.athena.omega.mvp.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 表单操作
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class BaseAction implements Serializable {
    /**
     * ID
     */
    private String id;
    /**
     * 标签
     */
    private String label;
    /**
     * 图标
     */
    private String icon;
    /**
     * 类型
     */
    private Object onActivate;
    /**
     * 是否启用
     */
    private boolean enable = true;
}
