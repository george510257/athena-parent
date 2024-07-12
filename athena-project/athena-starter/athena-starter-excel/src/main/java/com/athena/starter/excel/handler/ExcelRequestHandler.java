package com.athena.starter.excel.handler;

import com.alibaba.excel.EasyExcel;
import com.athena.starter.excel.annotation.ExcelRequest;
import com.athena.starter.excel.listener.IReadListener;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Excel请求处理器
 */
public class ExcelRequestHandler implements HandlerMethodArgumentResolver {
    /**
     * 是否支持参数
     *
     * @param parameter 参数
     * @return 是否支持
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExcelRequest.class);
    }

    /**
     * 解析参数
     *
     * @param parameter     方法参数
     * @param mavContainer  模型视图容器
     * @param webRequest    web请求
     * @param binderFactory 绑定工厂
     * @return 参数
     * @throws Exception 异常
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取参数类型
        Class<?> parameterType = parameter.getParameterType();
        // 如果不是List类型, 则抛出异常
        if (!List.class.isAssignableFrom(parameterType)) {
            throw new IllegalArgumentException("Excel上传请求解析器错误, @ExcelRequest参数不是List, " + parameterType);
        }
        // 获取目标类型
        Class<?> targetType = getTargetType(parameter);
        // 获取参数注解
        ExcelRequest excelRequest = getExcelRequest(parameter);
        // 获取监听器
        IReadListener<?> readListener = getReadListener(excelRequest);
        // 获取文件流
        InputStream inputStream = getInputStream(webRequest, excelRequest.fileName());

        // 读取Excel
        EasyExcel.read(inputStream, targetType, readListener)
                .headRowNumber(excelRequest.headRowNumber())
                .ignoreEmptyRow(excelRequest.ignoreEmptyRow())
                .sheet()
                .doRead();

        // 创建绑定器
        WebDataBinder binder = binderFactory.createBinder(webRequest, readListener.getErrors(), "excel");
        ModelMap model = mavContainer.getModel();
        model.put(BindingResult.MODEL_KEY_PREFIX + "excel", binder.getBindingResult());

        // 返回结果
        return readListener.getList();
    }

    /**
     * 获取读取监听器
     *
     * @param excelRequest Excel请求
     * @return 读取监听器
     */
    private IReadListener<?> getReadListener(ExcelRequest excelRequest) {
        return BeanUtils.instantiateClass(excelRequest.readListener());
    }

    /**
     * 获取目标类型
     *
     * @param parameter 方法参数
     * @return 目标类型
     */
    private Class<?> getTargetType(MethodParameter parameter) {
        return ResolvableType.forMethodParameter(parameter).asCollection().resolveGeneric();
    }

    /**
     * 获取Excel请求
     *
     * @param parameter 方法参数
     * @return Excel请求
     */
    private ExcelRequest getExcelRequest(MethodParameter parameter) {
        ExcelRequest excelRequest = parameter.getParameterAnnotation(ExcelRequest.class);
        if (excelRequest == null) {
            throw new IllegalArgumentException("Excel上传请求解析器错误, @ExcelRequest参数为空");
        }
        return excelRequest;
    }

    /**
     * 获取文件流
     *
     * @param webRequest web请求
     * @param fileName   文件名
     * @return 文件流
     * @throws IOException IO异常
     */
    private InputStream getInputStream(NativeWebRequest webRequest, String fileName) throws IOException {
        // 获取MultipartRequest
        MultipartRequest multipartRequest = webRequest.getNativeRequest(MultipartRequest.class);
        if (multipartRequest == null) {
            return null;
        }
        // 获取文件
        MultipartFile file = multipartRequest.getFile(fileName);
        if (file == null) {
            return null;
        }
        // 返回文件流
        return file.getInputStream();
    }
}
