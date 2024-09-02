package com.athena.security.servlet.client.customizer;

import com.athena.security.servlet.client.delegate.DelegateOAuth2UserService;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 用户信息端点自定义器
 *
 * @author george
 */
@Component
public class UserInfoEndpointCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig> {
    /**
     * 委托 OAuth2 用户信息服务
     */
    @Resource
    private DelegateOAuth2UserService delegateOAuth2UserService;

    /**
     * 自定义 OAuth2 用户信息端点配置
     *
     * @param config 配置器
     */
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig config) {
        config.userService(delegateOAuth2UserService);
    }
}
