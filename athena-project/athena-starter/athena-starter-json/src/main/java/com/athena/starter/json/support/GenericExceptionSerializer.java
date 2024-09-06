package com.athena.starter.json.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenericExceptionSerializer<T extends Exception> extends JsonSerializer<T> {

    @Override
    public void serialize(T exception, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
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
        jsonGenerator.writeEndObject();
    }
}

