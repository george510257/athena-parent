package com.gls.athena.starter.json.support;

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

    /**
     * Deserializes JSON content into an exception object.
     *
     * @param jsonParser the JSON parser
     * @param context    the deserialization context
     * @return the deserialized exception
     * @throws IOException             if an I/O error occurs
     * @throws JsonProcessingException if a JSON processing error occurs
     */
    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        // Parse the JSON into a tree model for easier manipulation
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Extract the exception message from the JSON
        String message = node.get("message").asText();

        StackTraceElement[] stackTrace = null;

        // Check if the stack trace is present in the JSON and deserialize it
        if (node.has("stackTrace")) {
            JsonNode stackTraceNode = node.get("stackTrace");
            stackTrace = new StackTraceElement[stackTraceNode.size()];

            // Iterate over each element in the stack trace array
            for (int i = 0; i < stackTraceNode.size(); i++) {
                JsonNode elementNode = stackTraceNode.get(i);
                String className = elementNode.get("className").asText();
                String methodName = elementNode.get("methodName").asText();
                int lineNumber = elementNode.get("lineNumber").asInt();

                // Create a StackTraceElement from the JSON properties
                stackTrace[i] = new StackTraceElement(className, methodName, null, lineNumber);
            }
        }

        try {
            // Use reflection to create an instance of the exception with the message
            T exception = (T) Class.forName(node.get("type").asText()).getConstructor(String.class).newInstance(message);
            exception.setStackTrace(stackTrace);  // Set the deserialized stack trace
            return exception;
        } catch (Exception e) {
            throw new RuntimeException("Error during exception deserialization", e);
        }
    }
}

