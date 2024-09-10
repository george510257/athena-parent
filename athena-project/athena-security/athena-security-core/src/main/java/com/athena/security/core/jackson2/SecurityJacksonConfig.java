package com.athena.security.core.jackson2;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * Jackson 配置
 *
 * @author george
 */
@Configuration
public class SecurityJacksonConfig {
    /**
     * Jackson 配置
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer redisObjectMapperBuilderCustomizer() {
        return builder -> builder
                .modules(modules -> {
                    ClassLoader classLoader = SecurityJacksonConfig.class.getClassLoader();
                    modules.addAll(SecurityJackson2Modules.getModules(classLoader));
                    modules.add(new CoreSecurityModule());
                });
    }
}
