package com.gls.athena.starter.json.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;

/**
 * 异常序列化器
 *
 * @author george
 */
public class GenericExceptionSerializer<T extends Exception> extends JsonSerializer<T> {

    /**
     * 序列化异常
     *
     * @param exception     异常
     * @param jsonGenerator JSON  generator
     * @param serializers   序列化器提供者
     * @throws IOException IO  exception
     */
    @Override
    public void serialize(T exception, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        // 序列化异常信息
        jsonGenerator.writeStringField("message", exception.getMessage());
        // 序列化异常类型
        jsonGenerator.writeStringField("type", exception.getClass().getName());
        // 序列化异常栈追踪
        jsonGenerator.writeArrayFieldStart("stackTrace");
        for (StackTraceElement element : exception.getStackTrace()) {
            // 序列化每个栈追踪元素
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("className", element.getClassName());
            jsonGenerator.writeStringField("methodName", element.getMethodName());
            jsonGenerator.writeNumberField("lineNumber", element.getLineNumber());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

    /**
     * 序列化异常并包含类型信息
     *
     * @param exception     异常
     * @param jsonGenerator JSON generator
     * @param serializers   序列化器提供者
     * @param typeSer       类型序列化器
     * @throws IOException IO exception
     */
    @Override
    public void serializeWithType(T exception, JsonGenerator jsonGenerator, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        // 获取异常的类型ID并开始对象序列化
        WritableTypeId typeId = typeSer.typeId(exception, JsonToken.START_OBJECT);
        typeSer.writeTypePrefix(jsonGenerator, typeId);

        // 序列化异常对象
        serialize(exception, jsonGenerator, serializers);

        // 完成对象序列化并写入类型后缀
        typeSer.writeTypeSuffix(jsonGenerator, typeId);
    }
}

