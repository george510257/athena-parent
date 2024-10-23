package com.athena.omega.core.activation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * 异常描述器
 *
 * @param <Response> 响应
 * @author george
 */
@Data
@Accessors(chain = true)
public class ExceptionDescriber<Response> implements Serializable {
    /**
     * 标识
     */
    private String label;
    /**
     * 类 信息
     */
    private List<Class<?>> classes;
    /**
     * 异常执行器
     */
    private Function<Throwable, Response> process;

}
