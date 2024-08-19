package com.athena.security.servlet.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * 授权请求自定义器
 */
@Component
public class AuthorizeHttpRequestsCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    /**
     * 自定义
     *
     * @param registry 注册器
     */
    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {

        registry
                // 静态资源和登录页面不需要认证
                .requestMatchers(coreSecurityProperties.getIgnoreUrls()).permitAll()
                // 登录页面和登录请求不需要认证
                .requestMatchers(coreSecurityProperties.getFormLogin().getLoginPage()).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated();
    }
}
