package com.gls.athena.common.bean.support;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 方法日志DTO
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class MethodLogDto implements Serializable {
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private List<Object> args;
    /**
     * 结果
     */
    private Object result;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 异常
     */
    private String errorMessage;
    /**
     * 异常
     */
    private String throwable;
    /**
     * 方法日志类型
     */
    private String type;
    /**
     * 跟踪ID
     */
    private String traceId;

}
