package com.athena.security.servlet.resource.customizer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

/**
 * OAuth2资源服务器自定义
 *
 * @author george
 */
@Slf4j
@Component
public class OAuth2ResourceServerCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        // 默认配置
        configurer.opaqueToken(this::opaqueToken);
    }

    /**
     * 不透明令牌
     *
     * @param configurer 配置器
     */
    private void opaqueToken(OAuth2ResourceServerConfigurer<HttpSecurity>.OpaqueTokenConfigurer configurer) {
    }

}
