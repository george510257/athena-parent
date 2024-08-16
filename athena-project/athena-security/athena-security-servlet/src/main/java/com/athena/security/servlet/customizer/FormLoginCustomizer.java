package com.athena.security.servlet.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 表单登录自定义器
 */
@Component
public class FormLoginCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
    }
}
