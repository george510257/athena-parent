package com.athena.omega.core.activation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 请求参数
 *
 * @param <T> 请求参数类型
 * @author george
 */
@Data
@Accessors(chain = true)
public class RequestParameter<T> implements Serializable {
    /**
     * 参数转换器
     */
    private Function<T, Object> converter;
    /**
     * 参数类型
     */
    private Class<T> type;
    /**
     * 是否必须
     */
    private boolean required;
    /**
     * 描述
     */
    private String description;
    /**
     * 默认值
     */
    private T defaultValue;
    /**
     * 校验器
     */
    private Predicate<T> validator;
}
