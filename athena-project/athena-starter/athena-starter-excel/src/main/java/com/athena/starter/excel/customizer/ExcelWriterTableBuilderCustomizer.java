package com.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.athena.starter.excel.annotation.ExcelTable;

/**
 * Excel写入Table构建器自定义器
 *
 * @author george
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
     */
    public ExcelWriterTableBuilderCustomizer(ExcelTable excelTable) {
        super(excelTable.parameter());
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
    }
}
