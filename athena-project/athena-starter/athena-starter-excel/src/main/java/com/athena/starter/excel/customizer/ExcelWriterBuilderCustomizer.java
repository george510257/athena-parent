package com.athena.starter.excel.customizer;

import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.athena.common.core.base.ICustomizer;
import com.athena.starter.excel.annotation.ExcelResponse;

public class ExcelWriterBuilderCustomizer implements ICustomizer<ExcelWriterBuilder> {

    private ExcelResponse excelResponse;

    @Override
    public void customize(ExcelWriterBuilder excelWriterBuilder) {
        // 自动关闭流
        excelWriterBuilder.autoCloseStream(excelResponse.autoCloseStream());
        excelWriterBuilder.password(excelResponse.password());
        excelWriterBuilder.inMemory(excelResponse.inMemory());
        excelWriterBuilder.writeExcelOnException(excelResponse.writeExcelOnException());
        excelWriterBuilder.excelType(excelResponse.excelType());
    }
}
