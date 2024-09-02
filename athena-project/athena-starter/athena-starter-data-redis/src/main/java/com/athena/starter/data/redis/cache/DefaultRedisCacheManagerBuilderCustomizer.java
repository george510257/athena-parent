package com.athena.starter.data.redis.cache;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

/**
 * 默认Redis缓存管理器构建器自定义器
 *
 * @author george
 */
@Component
public class DefaultRedisCacheManagerBuilderCustomizer implements RedisCacheManagerBuilderCustomizer {
    /**
     * 缓存配置
     */
    @Resource
    private CacheProperties cacheProperties;
    /**
     * 默认缓存配置
     */
    @Resource
    private DefaultCacheProperties defaultCacheProperties;
    /**
     * 缓存过期注解处理器
     */
    @Resource
    private RedisSerializer<Object> jsonRedisSerializer;
    /**
     * 缓存过期注解处理器
     */
    @Resource
    private CacheExpireProcessor cacheExpireProcessor;

    /**
     * 定制Redis缓存管理器
     *
     * @param builder Redis缓存管理器构建器
     */
    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        // 默认缓存配置
        RedisCacheConfiguration config = builder.cacheDefaults()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                .computePrefixWith(cacheName -> Optional.ofNullable(cacheProperties.getRedis().getKeyPrefix()).orElse("") + cacheName + ":");
        // 设置默认缓存配置
        builder.cacheDefaults(config);

        // 从配置文件中读取缓存配置
        defaultCacheProperties.getExpires().forEach((cacheName, cacheExpire) -> {
            Duration duration = Duration.of(cacheExpire.getTimeToLive(), cacheExpire.getTimeUnit().toChronoUnit());
            builder.withCacheConfiguration(cacheName, config.entryTtl(duration));
        });

        // 从注解中读取缓存配置
        cacheExpireProcessor.getExpires().forEach((cacheName, duration) -> builder.withCacheConfiguration(cacheName, config.entryTtl(duration)));
    }
}
