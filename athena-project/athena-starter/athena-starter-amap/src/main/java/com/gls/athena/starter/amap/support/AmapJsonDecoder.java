package com.gls.athena.starter.amap.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * 高德地图 JSON 解码器
 *
 * @author george
 */
public class AmapJsonDecoder implements Decoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object decode(Response response, Type type) throws IOException {
        // 将响应体读取为字符串
        String body = convertInputStreamToString(response.body().asInputStream());
        // 替换空数组字符串为 null
        String modifiedBody = body.replaceAll("\\[\\s*\\]", "null");

        // 反序列化为目标类型
        return objectMapper.readValue(modifiedBody, objectMapper.constructType(type));
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

}
