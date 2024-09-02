package com.athena.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelMultiColumn 注解
 * <p>
 * 用于标记实体类中的字段，用于标记Excel中的多列
 *
 * @author george
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelMultiColumn {

    /**
     * 开始列
     *
     * @return 开始列
     */
    int start() default 0;

    /**
     * 结束列
     *
     * @return 结束列
     */
    int end() default Integer.MAX_VALUE;
}
