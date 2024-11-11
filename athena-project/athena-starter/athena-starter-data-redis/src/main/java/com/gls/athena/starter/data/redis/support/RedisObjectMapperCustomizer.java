package com.gls.athena.starter.data.redis.support;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Redis对象映射器自定义
 *
 * @author george
 */
@FunctionalInterface
public interface RedisObjectMapperCustomizer {

    /**
     * 自定义Redis对象映射器
     *
     * @param objectMapper Redis对象映射器
     */
    void customize(ObjectMapper objectMapper);
}
