package com.athena.omega.core.activation;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作描述器
 *
 * @param <Request>  请求
 * @param <Response> 响应
 * @author george
 */
public class OperationDescriber<Request, Response> {

    /**
     * 执行描述器
     */
    private final List<ExecutionDescriber<Request, Response>> executionDescribers = new ArrayList<>();
    /**
     * 异常描述器
     */
    private final List<ExceptionDescriber<Response>> exceptionDescribers = new ArrayList<>();
}
