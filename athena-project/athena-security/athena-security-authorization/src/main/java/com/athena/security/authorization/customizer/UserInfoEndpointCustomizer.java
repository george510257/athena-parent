package com.athena.security.authorization.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcUserInfoEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 用户信息端点自定义器
 */
@Component
public class UserInfoEndpointCustomizer implements Customizer<OidcUserInfoEndpointConfigurer> {
    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcUserInfoEndpointConfigurer configurer) {
    }
}
