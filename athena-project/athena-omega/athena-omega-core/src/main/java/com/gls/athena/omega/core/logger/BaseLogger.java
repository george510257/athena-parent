package com.gls.athena.omega.core.logger;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 日志
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public abstract class BaseLogger implements Serializable {
    /**
     * 日志消息
     */
    private String message;
    /**
     * 日志时间
     */
    private long time;
    /**
     * 目标类
     */
    private Class<?> targetClass;

    /**
     * 获取日志名称
     *
     * @return 日志名称
     */
    public abstract String getName();
}
