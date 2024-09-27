package com.athena.security.servlet.client.delegate;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

/**
 * 委托授权码令牌响应客户端
 *
 * @author george
 */
@Component
public class DelegateAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    /**
     * 自定义授权码令牌响应客户端定制器
     */
    @Resource
    private ObjectProvider<IAuthorizationCodeTokenResponseClientCustomizer> customizers;

    /**
     * 获取访问令牌响应
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 访问令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        // 获取注册标识
        String registrationId = authorizationCodeGrantRequest.getClientRegistration().getRegistrationId();
        DefaultAuthorizationCodeTokenResponseClient delegate = new DefaultAuthorizationCodeTokenResponseClient();
        // 根据注册标识获取授权码授权请求实体转换器
        IAuthorizationCodeTokenResponseClientCustomizer customizer = customizers.stream()
                .filter(c -> c.test(registrationId)).findFirst().orElse(null);
        // 如果存在定制器，则进行定制
        if (customizer != null) {
            customizer.customize(delegate);
            OAuth2AccessTokenResponse response = delegate.getTokenResponse(authorizationCodeGrantRequest);
            return customizer.customResponse(authorizationCodeGrantRequest, response);
        }
        return delegate.getTokenResponse(authorizationCodeGrantRequest);
    }

}
