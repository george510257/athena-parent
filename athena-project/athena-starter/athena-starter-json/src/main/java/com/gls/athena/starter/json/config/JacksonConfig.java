package com.gls.athena.starter.json.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.gls.athena.starter.json.support.DefaultDateFormat;
import com.gls.athena.starter.json.support.GenericExceptionMixin;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson 配置
 *
 * @author george
 */
@Configuration
public class JacksonConfig {

    /**
     * Jackson 全局配置
     *
     * @return 自定义配置类
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
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
        };
    }

    /**
     * Java 8 时间序列化与反序列化规则
     *
     * @return JavaTimeModule
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // ======================= 时间序列化规则 ===============================
        // yyyy-MM-dd HH:mm:ss
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        // yyyy-MM-dd
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        // HH:mm:ss
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));

        // ======================= 时间反序列化规则 ==============================
        // yyyy-MM-dd HH:mm:ss
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        // yyyy-MM-dd
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));
        // HH:mm:ss
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));

        // ======================= 异常序列化与反序列化规则 ========================
        javaTimeModule.setMixInAnnotation(Exception.class, GenericExceptionMixin.class);
        return javaTimeModule;
    }

}
