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

    @Override
    public void serialize(T exception, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStringField("message", exception.getMessage());
        jsonGenerator.writeStringField("type", exception.getClass().getName());
        jsonGenerator.writeArrayFieldStart("stackTrace");
        for (StackTraceElement element : exception.getStackTrace()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("className", element.getClassName());
            jsonGenerator.writeStringField("methodName", element.getMethodName());
            jsonGenerator.writeNumberField("lineNumber", element.getLineNumber());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }

    @Override
    public void serializeWithType(T exception, JsonGenerator jsonGenerator, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeId = typeSer.typeId(exception, JsonToken.START_OBJECT);
        typeSer.writeTypePrefix(jsonGenerator, typeId);
        serialize(exception, jsonGenerator, serializers);
        typeSer.writeTypeSuffix(jsonGenerator, typeId);
    }
}

