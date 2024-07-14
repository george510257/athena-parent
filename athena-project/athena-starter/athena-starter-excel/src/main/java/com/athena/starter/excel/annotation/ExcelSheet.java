package com.athena.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * ExcelSheet 注解
 * <p>
 * 用于标记实体类中的方法，用于标记Excel中的sheet
 * 用于标记方法，用于标记返回值为Excel的响应
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {

    ExcelParameter parameter() default @ExcelParameter;

    int sheetNo();

    /**
     * sheet名称
     *
     * @return 默认sheet1
     */
    String sheetName() default "sheet1";

    ExcelTable[] tables() default {};
}
