package com.gls.athena.security.servlet.customizer;

import com.gls.athena.security.core.properties.CoreSecurityProperties;
import com.gls.athena.security.servlet.rest.MobileAuthenticationConverter;
import com.gls.athena.security.servlet.rest.RestConfigurer;
import com.gls.athena.security.servlet.rest.UsernamePasswordAuthenticationConverter;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private CoreSecurityProperties coreSecurityProperties;

    @Override
    public void customize(RestConfigurer<HttpSecurity> configurer) {
        CoreSecurityProperties.Rest rest = coreSecurityProperties.getRest();
        configurer.loginPage(rest.getLoginPage())
                .loginProcessingUrl(rest.getLoginProcessingUrl())
                .authenticationConverters(this::authenticationConverters)
                .permitAll();
    }

    /**
     * 认证转换器
     *
     * @param authenticationConverters 认证转换器
     */
    private void authenticationConverters(List<AuthenticationConverter> authenticationConverters) {
        for (AuthenticationConverter authenticationConverter : authenticationConverters) {
            if (authenticationConverter instanceof MobileAuthenticationConverter mobileAuthenticationConverter) {
                // 设置手机号参数
                mobileAuthenticationConverter.setMobileParameter(coreSecurityProperties.getRest().getMobileParameter());
            } else if (authenticationConverter instanceof UsernamePasswordAuthenticationConverter usernamePasswordAuthenticationConverter) {
                // 设置用户名和密码参数
                usernamePasswordAuthenticationConverter.setUsernameParameter(coreSecurityProperties.getRest().getUsernameParameter());
                usernamePasswordAuthenticationConverter.setPasswordParameter(coreSecurityProperties.getRest().getPasswordParameter());
            }
        }
    }
}
