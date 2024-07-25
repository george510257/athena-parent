package com.athena.security.core.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * 安全JSON配置
 */
@AutoConfiguration
public class SecurityJsonConfig implements BeanClassLoaderAware {

    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    /**
     * 安全Jackson2ObjectMapperBuilderCustomizer
     *
     * @return 安全Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer securityJackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.modules(SecurityJackson2Modules.getModules(this.classLoader));
    }

    /**
     * 设置Bean类加载器
     *
     * @param classLoader 类加载器
     */
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
