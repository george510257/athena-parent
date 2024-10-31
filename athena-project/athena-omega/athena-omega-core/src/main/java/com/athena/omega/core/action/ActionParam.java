package com.athena.omega.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Action参数
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class ActionParam<T> implements Serializable {
    /**
     * 转换器
     */
    private Function<T, Object> converter;
    /**
     * 数据类型
     */
    private Class<T> type;
    /**
     * 是否必须
     */
    private boolean required;
    /**
     * 默认值
     */
    private T defaultValue;
    /**
     * 描述
     */
    private String description;
    /**
     * 校验
     */
    private Predicate<T> validator;

}
