package com.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.athena.starter.excel.annotation.ExcelTable;

import java.util.List;

/**
 * Excel写入Table构建器自定义器
 */
public class ExcelWriterTableBuilderCustomizer extends BaseExcelWriterBuilderCustomizer<ExcelWriterTableBuilder> {
    /**
     * ExcelTable
     */
    private final ExcelTable excelTable;

    /**
     * 构造函数
     *
     * @param excelTable ExcelTable
     * @param data       数据
     */
    public ExcelWriterTableBuilderCustomizer(ExcelTable excelTable, List<?> data) {
        super(excelTable.parameter(), data);
        this.excelTable = excelTable;
    }

    /**
     * 自定义
     *
     * @param builder ExcelWriterTableBuilder
     */
    @Override
    public void customize(ExcelWriterTableBuilder builder) {
        super.customize(builder);
        // tableNo
        builder.tableNo(excelTable.tableNo());
        // 写入数据
        builder.doWrite(getData());
    }
}
