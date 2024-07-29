package com.athena.security.authorization.customizer;

import com.athena.security.authorization.authentication.OAuth2PasswordAuthenticationConverter;
import com.athena.security.authorization.authentication.OAuth2PasswordAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Component;

/**
 * 令牌端点自定义器
 */
@Component
public class TokenEndpointCustomizer implements Customizer<OAuth2TokenEndpointConfigurer> {

    /**
     * 用户详情认证提供者
     */
    @Resource
    private AuthenticationManager authenticationManager;
    /**
     * 授权服务
     */
    @Resource
    private OAuth2AuthorizationService authorizationService;
    /**
     * 令牌生成器
     */
    @Resource
    private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2TokenEndpointConfigurer configurer) {

        // 添加密码模式
        configurer.authenticationProvider(new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator));
        configurer.accessTokenRequestConverter(new OAuth2PasswordAuthenticationConverter());
    }
}
