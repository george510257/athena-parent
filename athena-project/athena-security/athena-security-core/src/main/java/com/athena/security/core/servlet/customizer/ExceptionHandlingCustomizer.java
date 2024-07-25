package com.athena.security.core.servlet.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.stereotype.Component;

/**
 * 异常处理自定义器
 */
@Component
public class ExceptionHandlingCustomizer implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {

    }
}
