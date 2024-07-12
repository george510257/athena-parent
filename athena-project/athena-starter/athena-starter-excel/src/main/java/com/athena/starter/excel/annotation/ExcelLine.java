package com.athena.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelLine 注解
 * <p>
 * 用于标记实体类中的字段，用于标记Excel中的行
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelLine {
}
