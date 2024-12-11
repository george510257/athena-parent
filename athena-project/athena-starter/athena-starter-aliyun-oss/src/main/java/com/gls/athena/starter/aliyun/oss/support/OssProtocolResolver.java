package com.gls.athena.starter.aliyun.oss.support;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * oss协议解析器
 *
 * @author george
 */
public class OssProtocolResolver
        implements ProtocolResolver, ResourceLoaderAware {
    /**
     * 协议
     */
    public static final String PROTOCOL = "oss://";

    /**
     * 设置资源加载器
     *
     * @param resourceLoader 资源加载器
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        // 判断资源加载器是否支持
        if (DefaultResourceLoader.class.isAssignableFrom(resourceLoader.getClass())) {
            // 添加协议解析器
            ((DefaultResourceLoader) resourceLoader).addProtocolResolver(this);
        } else {
            throw new IllegalArgumentException("解析器不支持的资源加载器：" + resourceLoader.getClass());
        }

    }

    /**
     * 解析资源
     *
     * @param location       资源位置
     * @param resourceLoader 资源加载器
     * @return 资源
     */
    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        // 判断是否以协议开头
        if (!location.startsWith(PROTOCOL)) {
            return null;
        }
        // 返回oss资源
        return new OssResource(location);
    }
}
