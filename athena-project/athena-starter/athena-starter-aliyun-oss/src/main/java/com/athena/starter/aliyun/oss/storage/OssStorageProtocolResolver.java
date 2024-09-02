package com.athena.starter.aliyun.oss.storage;

import com.aliyun.oss.OSS;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.concurrent.Executor;

/**
 * 基于OSS的资源协议解析器
 *
 * @author george
 */
@RequiredArgsConstructor
public class OssStorageProtocolResolver implements ProtocolResolver, ResourceLoaderAware {
    /**
     * oss资源协议
     */
    private static final String PROTOCOL = "oss://";
    /**
     * OSS客户端
     */
    private final OSS oss;
    /**
     * OSS任务执行器
     */
    private final Executor executor;

    /**
     * 用于解析指定的位置并返回相应的资源对象
     *
     * @param resourceLoader 资源加载器
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        // 如果资源加载器是DefaultResourceLoader的实例，则添加协议解析器
        if (resourceLoader instanceof DefaultResourceLoader defaultResourceLoader) {
            defaultResourceLoader.addProtocolResolver(this);
            return;
        }
        // 如果资源加载器不是DefaultResourceLoader的实例，则抛出异常
        throw new IllegalArgumentException("The provided delegate resource loader is not an implementation of DefaultResourceLoader. Custom Protocol using oss:// prefix will not be enabled.");
    }

    /**
     * 用于解析指定的位置并返回相应的资源对象
     *
     * @param location       资源位置
     * @param resourceLoader 资源加载器
     * @return 资源对象
     */
    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        // 如果资源位置不是以oss://开头，则返回null
        if (!location.startsWith(PROTOCOL)) {
            return null;
        }
        // 解析存储位置
        final OssStorageLocation storageLocation = OssStorageLocation.ofLocation(location);
        // 返回OssStorageResource对象
        return new OssStorageResource(oss, executor, storageLocation);
    }
}
