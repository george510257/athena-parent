package com.gls.athena.starter.excel.config;

import com.gls.athena.starter.excel.handler.ExcelRequestHandler;
import com.gls.athena.starter.excel.handler.ExcelResponseHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel配置类
 *
 * @author george
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ExcelConfig {
    /**
     * 请求映射处理适配器
     */
    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * Excel配置
     */
    @PostConstruct
    public void init() {
        // 添加参数解析器
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(new ExcelRequestHandler());
        if (requestMappingHandlerAdapter.getArgumentResolvers() != null) {
            argumentResolvers.addAll(requestMappingHandlerAdapter.getArgumentResolvers());
        }
        requestMappingHandlerAdapter.setArgumentResolvers(argumentResolvers);

        // 添加返回值处理器
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        returnValueHandlers.add(new ExcelResponseHandler());
        if (requestMappingHandlerAdapter.getReturnValueHandlers() != null) {
            returnValueHandlers.addAll(requestMappingHandlerAdapter.getReturnValueHandlers());
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(returnValueHandlers);
    }
}
