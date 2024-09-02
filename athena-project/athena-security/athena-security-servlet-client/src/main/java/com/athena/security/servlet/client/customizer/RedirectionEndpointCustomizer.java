package com.athena.security.servlet.client.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 重定向端点自定义器
 *
 * @author george
 */
@Component
public class RedirectionEndpointCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>.RedirectionEndpointConfig> {
    /**
     * 自定义 OAuth2 重定向端点配置
     *
     * @param config 配置器
     */
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity>.RedirectionEndpointConfig config) {
    }
}
