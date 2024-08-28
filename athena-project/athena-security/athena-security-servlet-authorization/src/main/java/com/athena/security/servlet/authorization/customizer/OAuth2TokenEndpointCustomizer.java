package com.athena.security.servlet.authorization.customizer;

import com.athena.security.servlet.authorization.authentication.OAuth2PasswordAuthenticationConverter;
import com.athena.security.servlet.authorization.authentication.OAuth2PasswordAuthenticationProvider;
import com.athena.security.servlet.authorization.authentication.OAuth2SmsAuthenticationConverter;
import com.athena.security.servlet.authorization.authentication.OAuth2SmsAuthenticationProvider;
import com.athena.security.servlet.authorization.support.IUserService;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Component;

/**
 * 令牌端点自定义器
 */
@Component
public class OAuth2TokenEndpointCustomizer implements Customizer<OAuth2TokenEndpointConfigurer> {

    /**
     * 用户详情认证提供者
     */
    @Resource
    private IUserService userService;
    /**
     * 密码编码器
     */
    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * 授权服务
     */
    @Resource
    private OAuth2AuthorizationService authorizationService;
    /**
     * 令牌生成器
     */
    @Resource
    private OAuth2TokenGenerator<? extends OAuth2Token> oauth2TokenGenerator;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2TokenEndpointConfigurer configurer) {

        // 添加密码模式
        configurer.authenticationProvider(new OAuth2PasswordAuthenticationProvider(authorizationService, oauth2TokenGenerator, userService, passwordEncoder));
        configurer.accessTokenRequestConverter(new OAuth2PasswordAuthenticationConverter());

        // 添加短信模式
        configurer.authenticationProvider(new OAuth2SmsAuthenticationProvider(authorizationService, oauth2TokenGenerator, userService));
        configurer.accessTokenRequestConverter(new OAuth2SmsAuthenticationConverter());
    }
}
