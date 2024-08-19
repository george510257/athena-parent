package com.athena.security.servlet.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 表单登录自定义器
 */
@Component
public class FormLoginCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {

    private final CoreSecurityProperties.FormLogin formLogin;

    public FormLoginCustomizer(CoreSecurityProperties coreSecurityProperties) {
        this.formLogin = coreSecurityProperties.getFormLogin();
    }

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
        configurer.loginPage(formLogin.getLoginPage())
                .loginProcessingUrl(formLogin.getLoginProcessingUrl())
                .usernameParameter(formLogin.getUsernameParameter())
                .passwordParameter(formLogin.getPasswordParameter())
                .successForwardUrl(formLogin.getSuccessForwardUrl())
                .failureForwardUrl(formLogin.getFailureForwardUrl())
                .permitAll();
    }
}
