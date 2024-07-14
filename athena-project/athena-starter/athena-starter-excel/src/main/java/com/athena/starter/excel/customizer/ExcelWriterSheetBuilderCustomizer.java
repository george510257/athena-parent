package com.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.athena.starter.excel.annotation.ExcelSheet;
import com.athena.starter.excel.annotation.ExcelTable;

import java.util.List;

public class ExcelWriterSheetBuilderCustomizer extends BaseExcelWriterBuilderCustomizer<ExcelWriterSheetBuilder> {

    private final ExcelSheet excelSheet;

    public ExcelWriterSheetBuilderCustomizer(ExcelSheet excelSheet, List<?> data) {
        super(excelSheet.parameter(), data);
        this.excelSheet = excelSheet;
    }

    @Override
    public void customize(ExcelWriterSheetBuilder builder) {
        super.customize(builder);
        // sheetNo
        builder.sheetNo(excelSheet.sheetNo());
        // sheetName
        builder.sheetName(excelSheet.sheetName());

        // 写入数据
        if (excelSheet.tables() != null) {
            for (ExcelTable table : excelSheet.tables()) {
                ExcelWriterTableBuilder tableBuilder = builder.table(table.tableNo());
                List<?> data = (List<?>) getData().get(table.tableNo());
                ExcelWriterTableBuilderCustomizer tableBuilderCustomizer = new ExcelWriterTableBuilderCustomizer(table, data);
                tableBuilderCustomizer.customize(tableBuilder);
            }
        } else {
            builder.doWrite(getData());
        }
    }
}
