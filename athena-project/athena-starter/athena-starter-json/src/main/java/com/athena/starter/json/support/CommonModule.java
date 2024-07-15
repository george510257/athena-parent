package com.athena.starter.json.support;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Jackson 公共模块
 */
@Component
public class CommonModule extends SimpleModule {
    /**
     * 构造
     */
    public CommonModule() {

        super("CommonModule");
        // Long 类型转换为 String 类型
        addSerializer(Long.class, ToStringSerializer.instance);
        // LocalDateTime 序列化 yyyy-MM-dd HH:mm:ss
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        // LocalDate 序列化 yyyy-MM-dd
        addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        // LocalTime 序列化 HH:mm:ss
        addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
        // LocalDateTime 反序列化 yyyy-MM-dd HH:mm:ss
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        // LocalDate 反序列化 yyyy-MM-dd
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));
        // LocalTime 反序列化 HH:mm:ss
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));
    }

    /**
     * 设置模块
     *
     * @param context 上下文
     */
    @Override
    public void setupModule(SetupContext context) {
        ObjectMapper objectMapper = context.getOwner();
        // 设置默认类型 防止反序列化时出现类型丢失
        objectMapper.setDefaultTyping(null);
    }
}
