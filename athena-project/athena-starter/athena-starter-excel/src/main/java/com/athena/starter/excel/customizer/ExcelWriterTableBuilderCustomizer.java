package com.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.athena.starter.excel.annotation.ExcelTable;

import java.util.List;

public class ExcelWriterTableBuilderCustomizer extends BaseExcelWriterBuilderCustomizer<ExcelWriterTableBuilder> {

    private final ExcelTable excelTable;

    public ExcelWriterTableBuilderCustomizer(ExcelTable excelTable, List<?> data) {
        super(excelTable.parameter(), data);
        this.excelTable = excelTable;
    }

    @Override
    public void customize(ExcelWriterTableBuilder builder) {
        super.customize(builder);
        // tableNo
        builder.tableNo(excelTable.tableNo());
        // 写入数据
        builder.doWrite(getData());
    }
}
