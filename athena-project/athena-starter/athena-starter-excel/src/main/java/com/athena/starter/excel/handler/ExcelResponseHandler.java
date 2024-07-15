package com.athena.starter.excel.handler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.athena.starter.excel.annotation.ExcelResponse;
import com.athena.starter.excel.customizer.ExcelWriterBuilderCustomizer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Excel响应处理器
 */
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

        // 创建ExcelWriterBuilder对象
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outputStream);

        // 创建ExcelWriterBuilderCustomizer对象
        ExcelWriterBuilderCustomizer excelWriterBuilderCustomizer = new ExcelWriterBuilderCustomizer(excelResponse, data);

        // 自定义ExcelWriterBuilder
        excelWriterBuilderCustomizer.customize(excelWriterBuilder);

        // 设置请求已处理
        mavContainer.setRequestHandled(true);
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
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + excelResponse.filename() + excelResponse.excelType().getValue());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        // 获取OutputStream
        return response.getOutputStream();
    }
}
