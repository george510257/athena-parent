package com.athena.security.servlet.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import jakarta.annotation.Resource;
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
     * 核心安全属性配置
     */
    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
        CoreSecurityProperties.FormLogin formLogin = coreSecurityProperties.getFormLogin();
        // 配置表单登录
        configurer.loginPage(formLogin.getLoginPage())
                .loginProcessingUrl(formLogin.getLoginProcessingUrl())
                .usernameParameter(formLogin.getUsernameParameter())
                .passwordParameter(formLogin.getPasswordParameter())
                .permitAll();
    }
}
