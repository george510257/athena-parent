package com.athena.security.core.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@AutoConfiguration
public class SecurityJsonConfig implements BeanClassLoaderAware {

    private ClassLoader classLoader;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer securityJackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.modules(SecurityJackson2Modules.getModules(this.classLoader));
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
