package com.athena.security.servlet.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.mobile.MobileAuthenticationProvider;
import com.athena.security.servlet.mobile.MobileConfigurer;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 表单登录自定义器
 */
@Component
public class MobileCustomizer implements Customizer<MobileConfigurer<HttpSecurity>> {

    /**
     * 核心安全属性配置
     */
    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    /**
     * 用户详情服务
     */
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public void customize(MobileConfigurer<HttpSecurity> configurer) {
        CoreSecurityProperties.Mobile mobile = coreSecurityProperties.getMobile();
        configurer.loginPage(mobile.getLoginPage())
                .loginProcessingUrl(mobile.getLoginProcessingUrl())
                .mobileParameter(mobile.getMobileParameter())
                .authenticationProvider(new MobileAuthenticationProvider(userDetailsService))
                .permitAll();
    }
}
