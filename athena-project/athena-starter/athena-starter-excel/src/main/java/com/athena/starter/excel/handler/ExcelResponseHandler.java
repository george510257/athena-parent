package com.athena.starter.excel.handler;

import com.athena.starter.excel.annotation.ExcelResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

public class ExcelResponseHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelResponse.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 获取返回值类型
        Class<?> returnTypeClass = returnType.getParameterType();
        // 如果不是List类型, 则抛出异常
        if (!List.class.isAssignableFrom(returnTypeClass)) {
            throw new IllegalArgumentException("Excel响应处理器错误, @ExcelResponse参数不是List, " + returnTypeClass);
        }
    }
}
