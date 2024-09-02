package com.athena.starter.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;

import java.lang.annotation.*;

/**
 * ExcelResponse 注解
 * <p>
 * 用于标记响应参数，用于标记Excel响应参数
 *
 * @author george
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResponse {

    /**
     * 参数
     *
     * @return 默认参数
     */
    ExcelParameter parameter() default @ExcelParameter;

    /**
     * 是否自动关闭流
     *
     * @return 默认自动关闭
     */
    boolean autoCloseStream() default true;

    /**
     * 文件密码
     *
     * @return 默认无密码
     */
    String password() default "";

    /**
     * 是否内存操作
     *
     * @return 默认非内存操作
     */
    boolean inMemory() default false;

    /**
     * 是否写入异常
     *
     * @return 默认写入异常
     */
    boolean writeExcelOnException() default true;

    /**
     * 文件类型
     *
     * @return 默认xlsx
     */
    ExcelTypeEnum excelType() default ExcelTypeEnum.XLSX;

    /**
     * 编码
     *
     * @return 默认编码
     */
    String charset() default "";

    /**
     * 是否包含BOM
     *
     * @return 默认不包含BOM
     */
    boolean withBom() default false;

    /**
     * 模板
     *
     * @return 默认空
     */
    String template() default "";

    /**
     * excel sheet
     *
     * @return 默认sheet1
     */
    ExcelSheet[] sheets() default @ExcelSheet(sheetNo = 0);

    /**
     * 文件名
     *
     * @return 默认文件名
     */
    String filename();
}
