package com.athena.security.resource.servlet.customizer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

/**
 * OAuth2资源服务器自定义器
 */
@Component
public record OAuth2ResourceServerCustomizer(
        ObjectProvider<JwtCustomizer> jwtCustomizer,
        ObjectProvider<OpaqueTokenCustomizer> opaqueTokenCustomizer)
        implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        // 配置jwt
        jwtCustomizer.ifAvailable(configurer::jwt);
        // 配置不透明令牌
        opaqueTokenCustomizer.ifAvailable(configurer::opaqueToken);
    }
}
