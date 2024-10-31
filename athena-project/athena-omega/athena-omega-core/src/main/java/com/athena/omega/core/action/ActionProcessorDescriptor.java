package com.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Action处理器描述符
 *
 * @param <Request>
 * @param <Response>
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionProcessorDescriptor<Request, Response> {
    /**
     * 正常处理描述列表
     */
    private final List<ActionNormalProcessorDescriptor<Request, Response>> normalProcessorDescriptors = new ArrayList<>();
    /**
     * 异常处理描述列表
     */
    private final List<ActionExceptionProcessorDescriptor<Request, Response>> exceptionProcessorDescriptors = new ArrayList<>();
    /**
     * 最终处理描述
     */
    private Function<ActionRunner<Request, Response>, Response> finalProcessor;
}
