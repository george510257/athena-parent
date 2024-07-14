package com.athena.starter.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;

import java.lang.annotation.*;

/**
 * ExcelResponse 注解
 * <p>
 * 用于标记响应参数，用于标记Excel响应参数
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResponse {

    ExcelParameter parameter() default @ExcelParameter;

    boolean autoCloseStream() default true;

    String password() default "";

    boolean inMemory() default false;

    boolean writeExcelOnException() default true;

    ExcelTypeEnum excelType() default ExcelTypeEnum.XLSX;

    String charset() default "";

    boolean withBom() default false;

    String template() default "";

    ExcelSheet[] sheets() default @ExcelSheet(sheetNo = 0);

    String filename();
}
