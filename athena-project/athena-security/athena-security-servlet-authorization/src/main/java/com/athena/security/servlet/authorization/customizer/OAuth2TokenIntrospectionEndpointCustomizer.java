package com.athena.security.servlet.authorization.customizer;

import com.athena.security.servlet.authorization.support.DefaultOAuth2TokenIntrospectionAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenIntrospectionEndpointConfigurer;
import org.springframework.stereotype.Component;

/**
 * 令牌验证端点自定义器
 *
 * @author george
 */
@Component
public class OAuth2TokenIntrospectionEndpointCustomizer implements Customizer<OAuth2TokenIntrospectionEndpointConfigurer> {
    /**
     * 注册客户端存储库
     */
    @Resource
    private RegisteredClientRepository registeredClientRepository;
    /**
     * 授权服务
     */
    @Resource
    private OAuth2AuthorizationService authorizationService;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2TokenIntrospectionEndpointConfigurer configurer) {
        configurer.authenticationProvider(new DefaultOAuth2TokenIntrospectionAuthenticationProvider(registeredClientRepository, authorizationService));
    }
}
