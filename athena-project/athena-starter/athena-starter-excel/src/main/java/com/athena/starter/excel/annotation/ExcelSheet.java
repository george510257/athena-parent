package com.athena.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelSheet 注解
 * <p>
 * 用于标记实体类中的方法，用于标记Excel中的sheet
 * 用于标记方法，用于标记返回值为Excel的响应
 *
 * @author george
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {

    /**
     * 参数
     *
     * @return 默认参数
     */
    ExcelParameter parameter() default @ExcelParameter;

    /**
     * sheet编号
     *
     * @return 默认0
     */
    int sheetNo();

    /**
     * sheet名称
     *
     * @return 默认sheet1
     */
    String sheetName() default "sheet1";

    /**
     * excel表格
     *
     * @return 默认空
     */
    ExcelTable[] tables() default {};
}
