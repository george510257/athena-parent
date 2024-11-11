package com.gls.athena.starter.excel.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.gls.athena.starter.excel.annotation.ExcelResponse;
import com.gls.athena.starter.excel.annotation.ExcelSheet;
import com.gls.athena.starter.excel.customizer.ExcelWriterBuilderCustomizer;
import com.gls.athena.starter.excel.customizer.ExcelWriterSheetBuilderCustomizer;
import com.gls.athena.starter.excel.customizer.ExcelWriterTableBuilderCustomizer;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Excel响应处理器
 *
 * @author george
 */
@Slf4j
public class ExcelResponseHandler implements HandlerMethodReturnValueHandler {

    /**
     * 是否支持返回类型
     *
     * @param returnType 返回类型
     * @return 是否支持
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelResponse.class);
    }

    /**
     * 处理返回值
     *
     * @param returnValue  返回值
     * @param returnType   返回类型
     * @param mavContainer 模型视图容器
     * @param webRequest   web请求
     * @throws Exception 异常
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 获取返回值类型
        Class<?> returnTypeClass = returnType.getParameterType();
        // 如果不是List类型, 则抛出异常
        if (!List.class.isAssignableFrom(returnTypeClass)) {
            throw new IllegalArgumentException("Excel响应处理器错误, @ExcelResponse参数不是List, " + returnTypeClass);
        }
        // 获取返回值
        List<?> data = (List<?>) returnValue;

        // 获取注解
        ExcelResponse excelResponse = getExcelResponse(returnType);

        // 获取OutputStream
        OutputStream outputStream = getOutputStream(webRequest, excelResponse);

        // 创建ExcelWriter
        ExcelWriter excelWriter = getExcelWriter(outputStream, excelResponse);
        // 获取Sheet
        List<WriteSheet> writeSheetList = getExcelWriterSheet(excelResponse);
        // 获取Table
        Map<Integer, List<WriteTable>> writeTableMap = getExcelWriterTable(excelResponse);
        // 写入数据
        writeData(excelWriter, writeSheetList, writeTableMap, data);
        // 完成
        excelWriter.finish();

        // 设置请求已处理
        mavContainer.setRequestHandled(true);
    }

    /**
     * 写入数据
     *
     * @param excelWriter    ExcelWriter
     * @param writeSheetList WriteSheet列表
     * @param writeTableMap  WriteTableMap
     * @param data           数据
     */
    private void writeData(ExcelWriter excelWriter, List<WriteSheet> writeSheetList, Map<Integer, List<WriteTable>> writeTableMap, List<?> data) {
        // 写入数据
        for (WriteSheet writeSheet : writeSheetList) {
            List<?> sheetData;
            if (writeSheetList.size() == 1) {
                sheetData = data;
            } else {
                sheetData = (List<?>) data.get(writeSheet.getSheetNo());
            }

            List<WriteTable> writeTableList = writeTableMap.get(writeSheet.getSheetNo());

            if (CollUtil.isNotEmpty(writeTableList)) {
                for (WriteTable writeTable : writeTableList) {
                    List<?> tableData = (List<?>) sheetData.get(writeTable.getTableNo());
                    writeTable.setClazz(tableData.getFirst().getClass());
                    excelWriter.write(tableData, writeSheet, writeTable);
                }
            } else {
                writeSheet.setClazz(sheetData.getFirst().getClass());
                excelWriter.write(sheetData, writeSheet);
            }
        }
    }

    /**
     * 获取ExcelWriterTable
     *
     * @param excelResponse Excel响应
     */
    private Map<Integer, List<WriteTable>> getExcelWriterTable(ExcelResponse excelResponse) {
        return Arrays.stream(excelResponse.sheets()).collect(Collectors.toMap(ExcelSheet::sheetNo, excelSheet -> Arrays.stream(excelSheet.tables()).map(excelTable -> {
            ExcelWriterTableBuilder excelWriterTableBuilder = EasyExcel.writerTable(excelTable.tableNo());
            ExcelWriterTableBuilderCustomizer excelWriterTableBuilderCustomizer = new ExcelWriterTableBuilderCustomizer(excelTable);
            excelWriterTableBuilderCustomizer.customize(excelWriterTableBuilder);
            return excelWriterTableBuilder.build();
        }).toList()));
    }

    /**
     * 获取ExcelWriterSheet
     *
     * @param excelResponse Excel响应
     * @return ExcelWriterSheet
     */
    private List<WriteSheet> getExcelWriterSheet(ExcelResponse excelResponse) {
        return Arrays.stream(excelResponse.sheets()).map(excelSheet -> {
            ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.writerSheet(excelSheet.sheetNo(), excelSheet.sheetName());
            ExcelWriterSheetBuilderCustomizer excelWriterSheetBuilderCustomizer = new ExcelWriterSheetBuilderCustomizer(excelSheet);
            excelWriterSheetBuilderCustomizer.customize(excelWriterSheetBuilder);
            return excelWriterSheetBuilder.build();
        }).toList();

    }

    /**
     * 获取ExcelWriter
     *
     * @param outputStream  输出流
     * @param excelResponse Excel响应
     * @return ExcelWriter
     */
    private ExcelWriter getExcelWriter(OutputStream outputStream, ExcelResponse excelResponse) {
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outputStream);
        ExcelWriterBuilderCustomizer excelWriterBuilderCustomizer = new ExcelWriterBuilderCustomizer(excelResponse);
        excelWriterBuilderCustomizer.customize(excelWriterBuilder);
        return excelWriterBuilder.build();
    }

    /**
     * 获取Excel响应注解
     *
     * @param returnType 返回类型
     * @return Excel响应注解
     */
    private ExcelResponse getExcelResponse(MethodParameter returnType) {
        ExcelResponse excelResponse = returnType.getMethodAnnotation(ExcelResponse.class);
        if (excelResponse == null) {
            throw new IllegalArgumentException("Excel响应处理器错误, @ExcelResponse注解为空");
        }
        return excelResponse;
    }

    /**
     * 获取OutputStream
     *
     * @param webRequest    web请求
     * @param excelResponse Excel响应
     * @return OutputStream
     * @throws IOException IO异常
     */
    private OutputStream getOutputStream(NativeWebRequest webRequest, ExcelResponse excelResponse) throws IOException {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        if (response == null) {
            throw new IllegalArgumentException("Excel响应处理器错误, HttpServletResponse为空");
        }
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLUtil.encode(excelResponse.filename(), StandardCharsets.UTF_8) + excelResponse.excelType().getValue());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        // 获取OutputStream
        return response.getOutputStream();
    }
}
