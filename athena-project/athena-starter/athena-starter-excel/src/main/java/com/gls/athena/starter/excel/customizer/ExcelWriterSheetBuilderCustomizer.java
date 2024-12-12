package com.gls.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.gls.athena.starter.excel.annotation.ExcelSheet;

/**
 * Excel写入Sheet构建器自定义器
 *
 * @author george
 */
public class ExcelWriterSheetBuilderCustomizer extends ExcelWriterParameterBuilderCustomizer<ExcelWriterSheetBuilder> {
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

    /**
     * 定制
     *
     * @param builder 定制对象
     */
    @Override
    public void customize(ExcelWriterSheetBuilder builder) {
        super.customize(builder);
        // sheetNo
        builder.sheetNo(excelSheet.sheetNo());
        // sheetName
        builder.sheetName(excelSheet.sheetName());
    }
}
