package com.athena.security.servlet.client.customizer;

import com.athena.security.servlet.client.delegate.DelegateAuthorizationCodeTokenResponseClient;
import com.athena.security.servlet.client.delegate.DelegateAuthorizationRequestResolver;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.stereotype.Component;

/**
 * OAuth2 客户端自定义器
 *
 * @author george
 */
@Component
public class OAuth2ClientCustomizer implements Customizer<OAuth2ClientConfigurer<HttpSecurity>> {
    /**
     * 委托授权请求解析器
     */
    @Resource
    private DelegateAuthorizationRequestResolver authorizationRequestResolver;
    /**
     * 委托授权码令牌响应客户端
     */
    @Resource
    private DelegateAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient;

    /**
     * 自定义 OAuth2 客户端配置
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ClientConfigurer<HttpSecurity> configurer) {
        // 自定义 OAuth2 授权码授权配置
        configurer.authorizationCodeGrant(this::authorizationCodeGrant);
    }

    /**
     * 自定义 OAuth2 授权码授权配置
     *
     * @param configurer 配置器
     */
    private void authorizationCodeGrant(OAuth2ClientConfigurer<HttpSecurity>.AuthorizationCodeGrantConfigurer configurer) {
        // 设置委托授权请求解析器
        configurer.authorizationRequestResolver(authorizationRequestResolver);
        // 设置委托授权码令牌响应客户端
        configurer.accessTokenResponseClient(authorizationCodeTokenResponseClient);
    }
}
