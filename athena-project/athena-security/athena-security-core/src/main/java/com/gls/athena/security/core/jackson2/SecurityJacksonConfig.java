package com.gls.athena.security.core.jackson2;

import com.gls.athena.starter.data.redis.support.RedisObjectMapperCustomizer;
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
     * Redis对象映射器自定义
     *
     * @return RedisObjectMapperCustomizer
     */
    @Bean
    public RedisObjectMapperCustomizer securityRedisObjectMapperCustomizer() {
        return objectMapper -> {
            // 注册Security模块
            SecurityJackson2Modules.getModules(getClass().getClassLoader())
                    .forEach(objectMapper::registerModule);
            // 注册CoreSecurity模块
            objectMapper.registerModule(new CoreSecurityModule());
        };
    }
}
