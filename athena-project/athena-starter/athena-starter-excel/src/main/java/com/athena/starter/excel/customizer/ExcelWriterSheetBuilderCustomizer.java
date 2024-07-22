package com.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.athena.starter.excel.annotation.ExcelSheet;

/**
 * Excel写入Sheet构建器自定义器
 */
public class ExcelWriterSheetBuilderCustomizer extends BaseExcelWriterBuilderCustomizer<ExcelWriterSheetBuilder> {
    /**
     * ExcelSheet
     */
    private final ExcelSheet excelSheet;

    /**
     * 构造函数
     *
     * @param excelSheet ExcelSheet
     */
    public ExcelWriterSheetBuilderCustomizer(ExcelSheet excelSheet) {
        super(excelSheet.parameter());
        this.excelSheet = excelSheet;
    }

    @Override
    public void customize(ExcelWriterSheetBuilder builder) {
        super.customize(builder);
        // sheetNo
        builder.sheetNo(excelSheet.sheetNo());
        // sheetName
        builder.sheetName(excelSheet.sheetName());
    }
}
