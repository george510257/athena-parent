package com.gls.athena.starter.aliyun.oss.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
        implements ProtocolResolver, BeanFactoryPostProcessor, ResourceLoaderAware {
    /**
     * 协议
     */
    public static final String PROTOCOL = "oss://";
    /**
     * bean工厂
     */
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * 设置资源加载器
     *
     * @param beanFactory bean工厂
     * @throws BeansException bean异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 设置资源加载器
     *
     * @param resourceLoader 资源加载器
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        if (DefaultResourceLoader.class.isAssignableFrom(resourceLoader.getClass())) {
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
        if (!location.startsWith(PROTOCOL)) {
            return null;
        }
        return new OssResource(location, beanFactory);
    }
}
