package com.athena.starter.data.redis.session;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Session配置
 */
@Configuration
public class SessionConfig {
    /**
     * springSessionDefaultRedisSerializer
     */
    private static final String SPRING_SESSION_DEFAULT_REDIS_SERIALIZER = "springSessionDefaultRedisSerializer";

    /**
     * json序列化器
     *
     * @param jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder 实例
     * @return json序列化器
     */
    @Bean(SPRING_SESSION_DEFAULT_REDIS_SERIALIZER)
    @ConditionalOnMissingBean
    public RedisSerializer<Object> jsonRedisSerializer(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 设置可见度
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 启用默认类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

}
