package com.athena.starter.swagger.config;

import com.athena.starter.swagger.converter.OpenApiConverter;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerConfig {
    @Resource
    private SwaggerProperties swaggerProperties;

    @Resource
    private OpenApiConverter openApiConverter;

    /**
     * Swagger配置
     */
    @Bean
    public OpenAPI openAPI() {
        return openApiConverter.convert(swaggerProperties.getOpenApi());
    }
}
