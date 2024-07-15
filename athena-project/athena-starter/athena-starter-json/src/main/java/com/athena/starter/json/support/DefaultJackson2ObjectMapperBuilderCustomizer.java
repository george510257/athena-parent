package com.athena.starter.json.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 默认 Jackson2ObjectMapperBuilder 自定义器
 */
@Component
public class DefaultJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    /**
     * Jackson 模块
     */
    @Resource
    private List<Module> modules;

    /**
     * 定制 Jackson2ObjectMapperBuilder
     *
     * @param builder Jackson2ObjectMapperBuilder 对象
     */
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        // 设置全局地区
        builder.locale(Locale.CHINA);
        // 设置全局时区
        builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        // 设置全局日期格式
        builder.dateFormat(new DefaultDateFormat());
        // 反序列化时忽略未知属性
        builder.failOnUnknownProperties(false);
        // 序列化时忽略值为 null 的属性
        builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        // 设置全局序列化模块
        builder.modules(modules);
    }
}
