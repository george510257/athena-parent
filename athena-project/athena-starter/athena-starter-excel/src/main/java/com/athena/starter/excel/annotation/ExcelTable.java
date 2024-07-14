package com.athena.starter.excel.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTable {

    ExcelParameter parameter() default @ExcelParameter;

    int tableNo();
}
