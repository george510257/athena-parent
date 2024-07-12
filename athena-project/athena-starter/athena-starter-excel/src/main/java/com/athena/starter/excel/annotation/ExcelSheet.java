package com.athena.starter.excel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.athena.common.core.base.ICustomizer;

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

    /**
     * sheet名称
     *
     * @return 默认sheet1
     */
    String sheetName() default "sheet1";

    /**
     * 包含字段
     *
     * @return 默认为空
     */
    String[] include() default {};

    /**
     * 排除字段
     *
     * @return 默认为空
     */
    String[] exclude() default {};

    /**
     * 写处理器
     *
     * @return 默认为空
     */
    Class<? extends WriteHandler>[] writeHandler() default {};

    /**
     * 转换器
     *
     * @return 默认为空
     */
    Class<? extends Converter<?>>[] converter() default {};

    /**
     * 自定义处理
     *
     * @return 默认为空
     */
    Class<? extends ICustomizer<ExcelWriterSheetBuilder>>[] customizer() default {};

    /**
     * 是否自动合并头
     *
     * @return 默认自动合并
     */
    boolean autoMergeHead() default true;

    /**
     * 是否填写标题
     *
     * @return 默认填写标题
     */
    boolean writeHead() default true;
}
