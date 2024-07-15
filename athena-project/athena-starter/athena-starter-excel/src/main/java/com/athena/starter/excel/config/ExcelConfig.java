package com.athena.starter.excel.config;

import com.athena.starter.excel.handler.ExcelRequestHandler;
import com.athena.starter.excel.handler.ExcelResponseHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Excel配置类
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ExcelConfig implements WebMvcConfigurer {

    /**
     * 添加参数解析器
     *
     * @param resolvers initially an empty list
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ExcelRequestHandler());
    }

    /**
     * 添加返回值处理器
     *
     * @param handlers initially an empty list
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new ExcelResponseHandler());
    }
}
