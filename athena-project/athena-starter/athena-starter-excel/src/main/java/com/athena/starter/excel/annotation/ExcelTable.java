package com.athena.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelTable 注解
 * <p>
 * 用于标记实体类中的方法，用于标记Excel中的table
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTable {

    /**
     * 参数
     *
     * @return 默认参数
     */
    ExcelParameter parameter() default @ExcelParameter;

    /**
     * table编号
     *
     * @return 默认0
     */
    int tableNo();
}
