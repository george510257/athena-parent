package com.athena.security.servlet.client.customizer;

import com.athena.security.servlet.client.delegate.DelegateAuthorizationCodeTokenResponseClient;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * 令牌端点自定义器
 */
@Component
public class TokenEndpointCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>.TokenEndpointConfig> {

    @Resource
    private DelegateAuthorizationCodeTokenResponseClient delegateAuthorizationCodeTokenResponseClient;
    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity>.TokenEndpointConfig config) {
        config.accessTokenResponseClient(delegateAuthorizationCodeTokenResponseClient);
    }

}
