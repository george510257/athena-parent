package com.athena.starter.swagger.config;

import com.athena.starter.swagger.converter.OpenAPIConverter;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerConfig {
    /**
     * Swagger配置
     */
    @Resource
    private SwaggerProperties swaggerProperties;
    /**
     * openApi转换器
     */
    @Resource
    private OpenAPIConverter openApiConverter;

    /**
     * openAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return openApiConverter.convert(swaggerProperties.getOpenApi());
    }
}
