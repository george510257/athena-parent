package com.gls.athena.security.servlet.client.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

/**
 * 微信 HTTP 消息转换器
 *
 * @author george
 */
public class WechatHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public WechatHttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public WechatHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        // 设置支持的媒体类型
        setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
    }
}
