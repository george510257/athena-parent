package com.athena.security.servlet.client.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 用户信息端点自定义器
 */
@Component
public class UserInfoEndpointCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig> {
    /**
     * 自定义 OAuth2 用户信息端点配置
     *
     * @param config 配置器
     */
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig config) {
    }
}
