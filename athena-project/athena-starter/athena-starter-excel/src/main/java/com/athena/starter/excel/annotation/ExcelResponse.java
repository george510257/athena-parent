package com.athena.starter.excel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.athena.common.core.base.ICustomizer;

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

    /**
     * 文件名称
     *
     * @return 默认文件名称
     */
    String fileName() default "excel";

    /**
     * 是否自动关闭流
     *
     * @return 默认自动关闭
     */
    boolean autoCloseStream() default true;

    /**
     * 文件类型
     *
     * @return 默认xlsx
     */
    ExcelTypeEnum excelType() default ExcelTypeEnum.XLSX;

    /**
     * 是否内存操作
     *
     * @return 默认内存操作
     */
    boolean inMemory() default true;

    /**
     * 文件密码
     *
     * @return 默认无密码
     */
    String password() default "";

    /**
     * 是否在异常时写入
     *
     * @return 默认不写入
     */
    boolean writeExcelOnException() default false;

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
     * 写入处理器
     *
     * @return 默认为空
     */
    Class<? extends WriteHandler>[] writeHandler() default {};

    /**
     * 转换器
     *
     * @return 默认为空
     */
    Class<? extends Converter>[] converter() default {};

    /**
     * 模板
     *
     * @return 默认为空
     */
    String template() default "";

    /**
     * 自定义处理
     *
     * @return 默认为空
     */
    Class<? extends ICustomizer<ExcelWriterBuilder>>[] customizer() default {};

    /**
     * sheet
     *
     * @return 默认为空
     */
    ExcelSheet[] sheets() default @ExcelSheet;

    /**
     * 自动填充
     *
     * @return 默认不自动填充
     */
    boolean automaticFill() default false;
}
