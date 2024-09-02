package com.athena.starter.excel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CacheLocationEnum;
import com.alibaba.excel.write.handler.WriteHandler;

import java.lang.annotation.*;

/**
 * ExcelParameter 注解
 * <p>
 * 用于标记实体类中的方法，用于标记Excel的参数
 *
 * @author george
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelParameter {
    /**
     * 表头
     *
     * @return 默认空
     */
    String[] head() default {};

    /**
     * 转换器
     *
     * @return 默认空
     */
    Class<? extends Converter<?>>[] converter() default {};

    /**
     * excel中时间是存储1900年起的一个双精度浮点数，但是有时候默认开始日期是1904，所以设置这个值改成默认1904年开始
     *
     * @return 默认false
     */
    boolean use1904windowing() default false;

    /**
     * 区域
     *
     * @return 默认zh_CN
     */
    String locale() default "";

    /**
     * 字段缓存位置
     *
     * @return 默认THREAD_LOCAL
     */
    CacheLocationEnum filedCacheLocation() default CacheLocationEnum.THREAD_LOCAL;

    /**
     * 自动去除空格 会对头、读取数据等进行自动trim
     *
     * @return 默认true
     */
    boolean autoTrim() default true;

    /**
     * 写入到excel和上面空开几行
     *
     * @return 默认0
     */
    int relativeHeadRowIndex() default 0;

    /**
     * 是否需要写入头到excel
     *
     * @return 默认true
     */
    boolean needHead() default true;

    /**
     * 写处理器
     *
     * @return 默认空
     */
    Class<? extends WriteHandler>[] writeHandler() default {};

    /**
     * 是否使用默认样式
     *
     * @return 默认true
     */
    boolean useDefaultStyle() default true;

    /**
     * 是否自动合并头
     *
     * @return 默认true
     */
    boolean automaticMergeHead() default true;

    /**
     * 需要排除对象中的index的数据
     *
     * @return 默认空
     */
    int[] excludeColumnIndexes() default {};

    /**
     * 需要排除对象中的字段的数据
     *
     * @return 默认空
     */
    String[] excludeColumnFieldNames() default {};

    /**
     * 只要导出对象中的index的数据
     *
     * @return 默认空
     */
    int[] includeColumnIndexes() default {};

    /**
     * 只要导出对象中的字段的数据
     *
     * @return 默认空
     */
    String[] includeColumnFieldNames() default {};

    /**
     * 在使用了参数includeColumnFieldNames 或者 includeColumnIndexes的时候，会根据传入集合的顺序排序
     *
     * @return 默认false
     */
    boolean orderByIncludeColumn() default false;
}
