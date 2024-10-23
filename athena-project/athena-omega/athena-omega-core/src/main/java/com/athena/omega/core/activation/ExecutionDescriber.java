package com.athena.omega.core.activation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 执行描述器
 *
 * @param <Request>  请求
 * @param <Response> 响应
 * @author george
 */
@Data
@Accessors(chain = true)
public class ExecutionDescriber<Request, Response> implements Serializable {
    /**
     * 类型
     */
    private String type;
    /**
     * 标识
     */
    private String flag;
    /**
     * 描述
     */
    private String description;
    /**
     * 参数
     */
    private Map<String, Object> params;
    /**
     * 执行器
     */
    private Consumer<ActivationRunner<Request, Response>> process;
}
