package com.gls.athena.starter.web.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * WebMvc配置
 *
 * @author george
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Jackson2对象映射构建器
     */
    @Resource
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    /**
     * 增加GET请求参数中时间类型转换
     *
     * @param registry 注册器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 增加GET请求参数中时间类型转换
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        // 设置时间格式
        registrar.setTimeFormatter(DatePattern.NORM_TIME_FORMATTER);
        // 设置日期格式
        registrar.setDateFormatter(DatePattern.NORM_DATE_FORMATTER);
        // 设置日期时间格式
        registrar.setDateTimeFormatter(DatePattern.NORM_DATETIME_FORMATTER);
        // 注册时间格式
        registrar.registerFormatters(registry);
    }

    /**
     * 配置消息转换器
     *
     * @param converters 消息转换器列表
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除默认的Jackson消息转换器
        converters.removeIf(converter -> converter instanceof AbstractJackson2HttpMessageConverter);
        // 创建Long类型序列化为String的模块
        SimpleModule simpleModule = new SimpleModule();
        // Long类型序列化为String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        // Long类型序列化为String
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 创建ObjectMapper
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 注册模块
        objectMapper.registerModule(simpleModule);
        // 禁用默认类型
        objectMapper.deactivateDefaultTyping();
        // 添加自定义的Jackson消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        // 设置默认字符集
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 添加到消息转换器列表
        converters.add(converter);
    }
}
