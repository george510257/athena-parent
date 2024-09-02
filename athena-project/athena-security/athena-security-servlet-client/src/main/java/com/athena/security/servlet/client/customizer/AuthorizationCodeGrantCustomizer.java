package com.athena.security.servlet.client.customizer;

import com.athena.security.servlet.client.delegate.DelegateAuthorizationCodeTokenResponseClient;
import com.athena.security.servlet.client.delegate.DelegateAuthorizationRequestResolver;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.stereotype.Component;

/**
 * 授权码授权自定义器
 *
 * @author george
 */
@Component
public class AuthorizationCodeGrantCustomizer implements Customizer<OAuth2ClientConfigurer<HttpSecurity>.AuthorizationCodeGrantConfigurer> {
    /**
     * 委托授权请求解析器
     */
    @Resource
    private DelegateAuthorizationRequestResolver delegateAuthorizationRequestResolver;
    /**
     * 委托授权码令牌响应客户端
     */
    @Resource
    private DelegateAuthorizationCodeTokenResponseClient delegateAuthorizationCodeTokenResponseClient;

    /**
     * 自定义 OAuth2 授权码授权配置
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ClientConfigurer<HttpSecurity>.AuthorizationCodeGrantConfigurer configurer) {
        // 设置委托授权请求解析器
        configurer.authorizationRequestResolver(delegateAuthorizationRequestResolver);
        // 设置委托授权码令牌响应客户端
        configurer.accessTokenResponseClient(delegateAuthorizationCodeTokenResponseClient);
    }
}
