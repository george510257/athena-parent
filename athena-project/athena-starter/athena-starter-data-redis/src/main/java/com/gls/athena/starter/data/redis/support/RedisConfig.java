package com.gls.athena.starter.data.redis.support;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 配置类
 *
 * @author george
 */
@Configuration
public class RedisConfig {
    /**
     * RedisTemplate 对象
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * JSON序列化器
     */
    @Resource
    private RedisSerializer<Object> jsonRedisSerializer;

    /**
     * RedisTemplate 配置
     */
    @PostConstruct
    public void init() {
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
    }

}
