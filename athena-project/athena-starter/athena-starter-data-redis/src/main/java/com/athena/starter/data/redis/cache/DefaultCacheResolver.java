package com.athena.starter.data.redis.cache;

import cn.hutool.core.util.StrUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 默认缓存解析器
 */
@Component
public class DefaultCacheResolver extends AbstractCacheResolver {
    /**
     * 构造方法
     *
     * @param cacheManager 缓存管理器
     */
    public DefaultCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    /**
     * 获取缓存名称
     *
     * @param beanClass 类
     * @param method    方法
     * @return 缓存名称
     */
    public static List<String> getCacheNames(Class<?> beanClass, Method method) {
        // 获取类名
        String className = beanClass.getSimpleName();
        // 将类名转换为下划线格式
        String upperCase = StrUtil.toSymbolCase(className, '-');
        // 获取缓存操作源
        AnnotationCacheOperationSource cacheOperationSource = new AnnotationCacheOperationSource(false);
        // 获取缓存名称
        List<String> cacheNames = Optional.ofNullable(cacheOperationSource.getCacheOperations(method, beanClass))
                .map(cacheOperations -> cacheOperations.stream()
                        .map(CacheOperation::getCacheNames)
                        .flatMap(Collection::stream)
                        .distinct()
                        .map(cacheName -> upperCase + ":" + cacheName)
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        // 如果缓存名称为空则添加默认缓存名称
        if (cacheNames.isEmpty()) {
            cacheNames.add(upperCase + ":default");
        }
        // 返回缓存名称
        return cacheNames;
    }

    /**
     * 获取缓存名称
     *
     * @param context 上下文
     * @return 缓存名称
     */
    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        return getCacheNames(context.getTarget().getClass(), context.getMethod());
    }
}
