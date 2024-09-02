package com.athena.starter.excel.annotation;

import com.athena.starter.excel.listener.DefaultReadListener;
import com.athena.starter.excel.listener.IReadListener;

import java.lang.annotation.*;

/**
 * ExcelRequest 注解
 * <p>
 * 用于标记请求参数，用于标记Excel请求参数
 *
 * @author george
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelRequest {

    /**
     * 文件名
     */
    String fileName() default "file";

    /**
     * 标题行数
     */
    int headRowNumber() default 1;

    /**
     * 是否跳过空行
     */
    boolean ignoreEmptyRow() default true;

    /**
     * 读取监听器
     */
    Class<? extends IReadListener> readListener() default DefaultReadListener.class;
}
