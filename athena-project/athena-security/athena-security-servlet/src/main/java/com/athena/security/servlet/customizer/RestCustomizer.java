package com.athena.security.servlet.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.rest.RestConfigurer;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * 表单登录自定义器
 */
@Component
public class RestCustomizer implements Customizer<RestConfigurer<HttpSecurity>> {

    /**
     * 核心安全属性配置
     */
    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Override
    public void customize(RestConfigurer<HttpSecurity> configurer) {
        CoreSecurityProperties.Rest rest = coreSecurityProperties.getRest();
        configurer.loginPage(rest.getLoginPage())
                .loginProcessingUrl(rest.getLoginProcessingUrl())
                .permitAll();
    }
}
