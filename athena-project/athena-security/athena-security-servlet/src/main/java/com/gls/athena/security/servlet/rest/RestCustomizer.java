package com.gls.athena.security.servlet.rest;

import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * 表单登录自定义器
 *
 * @author george
 */
@Component
public class RestCustomizer implements Customizer<RestConfigurer<HttpSecurity>> {

    /**
     * 核心安全属性配置
     */
    @Resource
    private RestProperties restProperties;

    @Override
    public void customize(RestConfigurer<HttpSecurity> configurer) {
        configurer.loginPage(restProperties.getLoginPage())
                .loginProcessingUrl(restProperties.getLoginProcessingUrl())
                .permitAll();
    }

}
