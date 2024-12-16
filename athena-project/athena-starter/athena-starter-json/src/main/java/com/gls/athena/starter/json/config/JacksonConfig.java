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
     * <p>
     * Jackson 是 spring boot 的默认 JSON 序列化与反序列化工具,
     * 通过该配置,可以对 Jackson 的行为进行设置。
     * </p>
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 设置 Jackson 的全局地区
            builder.locale(Locale.CHINA);
            // 设置 Jackson 的全局时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 设置 Jackson 的全局日期格式
            builder.dateFormat(new DefaultDateFormat());
            // 在反序列化时,忽略未知的属性,因此,在反序列化时,如果 JSON 中存在未知的字段,
            // Jackson 将忽略该字段,而不是抛出异常。
            builder.failOnUnknownProperties(false);
            // 序列化时,忽略值为 null 的字段,因此,在序列化时,如果对象中存在值为 null 的字段,
            // Jackson 将忽略该字段,而不是将其序列化为 null。
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

        // 该 Mixin 将在反序列化时,将 JSON 中的 message, type, stackTrace 三个字段反序列化为
        // Exception 对象的 message, className, stackTrace 三个字段
        return javaTimeModule;
    }

}
