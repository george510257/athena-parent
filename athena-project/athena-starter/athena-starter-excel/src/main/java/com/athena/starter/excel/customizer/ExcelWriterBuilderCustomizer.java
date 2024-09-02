package com.athena.starter.excel.customizer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.athena.starter.excel.annotation.ExcelResponse;

import java.nio.charset.Charset;

/**
 * Excel写入构建器自定义器
 *
 * @author george
 */
public class ExcelWriterBuilderCustomizer extends BaseExcelWriterBuilderCustomizer<ExcelWriterBuilder> {

    /**
     * Excel响应
     */
    private final ExcelResponse excelResponse;

    /**
     * 构造函数
     *
     * @param excelResponse Excel响应
     */
    public ExcelWriterBuilderCustomizer(ExcelResponse excelResponse) {
        super(excelResponse.parameter());
        this.excelResponse = excelResponse;
    }

    @Override
    public void customize(ExcelWriterBuilder builder) {
        // 调用父类方法
        super.customize(builder);
        // 自动关闭写入的流。
        builder.autoCloseStream(excelResponse.autoCloseStream());
        // 读取文件的密码
        if (StrUtil.isNotEmpty(excelResponse.password())) {
            builder.password(excelResponse.password());
        }
        // 是否在内存处理，默认会生成临时文件以节约内存。内存模式效率会更好，但是容易OOM
        builder.inMemory(excelResponse.inMemory());
        // 写入过程中抛出异常了，是否尝试把数据写入到excel
        builder.writeExcelOnException(excelResponse.writeExcelOnException());
        // 当前excel的类型,支持XLS、XLSX、CSV
        if (excelResponse.excelType() != null) {
            builder.excelType(excelResponse.excelType());
        }
        // 只有csv文件有用，写入文件的时候使用的编码
        if (StrUtil.isNotEmpty(excelResponse.charset())) {
            builder.charset(Charset.forName(excelResponse.charset()));
        }
        // 只有csv文件有用，是否添加bom头
        builder.withBom(excelResponse.withBom());
        // 模板路径
        if (StrUtil.isNotEmpty(excelResponse.template())) {
            builder.withTemplate(excelResponse.template());
        }
    }
}
