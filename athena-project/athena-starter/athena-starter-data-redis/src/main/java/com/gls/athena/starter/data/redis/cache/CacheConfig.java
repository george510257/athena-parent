package com.gls.athena.starter.data.redis.cache;

import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 *
 * @author george
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(DefaultCacheProperties.class)
public class CacheConfig implements CachingConfigurer {
    /**
     * 默认缓存解析器
     */
    @Resource
    private DefaultCacheResolver defaultCacheResolver;

    /**
     * 缓存解析器
     *
     * @return 缓存解析器
     */
    @Override
    public CacheResolver cacheResolver() {
        return defaultCacheResolver;
    }
}
