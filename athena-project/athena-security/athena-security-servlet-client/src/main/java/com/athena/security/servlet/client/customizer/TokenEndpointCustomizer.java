package com.athena.security.servlet.client.customizer;

import com.athena.security.servlet.client.delegate.DelegateAuthorizationCodeTokenResponseClient;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 令牌端点自定义器
 *
 * @author george
 */
@Component
public class TokenEndpointCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>.TokenEndpointConfig> {
    /**
     * 委托授权码令牌响应客户端
     */
    @Resource
    private DelegateAuthorizationCodeTokenResponseClient delegateAuthorizationCodeTokenResponseClient;

    /**
     * 自定义 OAuth2 令牌端点配置
     *
     * @param config 配置器
     */
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity>.TokenEndpointConfig config) {
        // 自定义 OAuth2 授权码令牌响应客户端
        config.accessTokenResponseClient(delegateAuthorizationCodeTokenResponseClient);
    }

}
