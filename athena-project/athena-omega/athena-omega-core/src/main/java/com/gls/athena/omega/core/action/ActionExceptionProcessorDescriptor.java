package com.gls.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Action异常处理器描述符
 *
 * @param <Request>  请求
 * @param <Response>
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionExceptionProcessorDescriptor<Request, Response> implements Predicate<Class<?>>, Serializable {
    /**
     * 异常类
     */
    private final List<Class<? extends Throwable>> classes = new ArrayList<>();
    /**
     * 标签
     */
    private String label;
    /**
     * 异常回调
     */
    private BiFunction<ActionRunner<Request, Response>, Throwable, Response> fallback;

    @Override
    public boolean test(Class<?> aClass) {
        return classes.contains(aClass);
    }
}
