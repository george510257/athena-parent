package com.athena.starter.json.support;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * 异常反序列化器
 *
 * @param <T> 异常类型
 * @author george
 */
public class GenericExceptionDeserializer<T extends Exception> extends JsonDeserializer<T> {

    private Class<T> exceptionClass;

    public GenericExceptionDeserializer(Class<T> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String message = node.get("message").asText();
        StackTraceElement[] stackTrace = null;

        if (node.has("stackTrace")) {
            JsonNode stackTraceNode = node.get("stackTrace");
            stackTrace = new StackTraceElement[stackTraceNode.size()];

            for (int i = 0; i < stackTraceNode.size(); i++) {
                JsonNode elementNode = stackTraceNode.get(i);
                String className = elementNode.get("className").asText();
                String methodName = elementNode.get("methodName").asText();
                int lineNumber = elementNode.get("lineNumber").asInt();
                stackTrace[i] = new StackTraceElement(className, methodName, null, lineNumber);
            }
        }

        try {
            // 通过反射创建异常实例
            T exception = exceptionClass.getConstructor(String.class).newInstance(message);
            exception.setStackTrace(stackTrace);
            return exception;
        } catch (Exception e) {
            throw new RuntimeException("Error during exception deserialization", e);
        }
    }
}

