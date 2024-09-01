package com.athena.security.servlet.client.delegate;

import jakarta.annotation.Resource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

/**
 * 委托授权码令牌响应客户端
 *
 * @author george
 */
@Component
public class DelegateAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
    /**
     * 授权码授权请求实体转换器列表
     */
    @Resource
    private Optional<IAuthorizationCodeGrantRequestConverter> requestEntityConverters;
    /**
     * accessToken 响应转换器列表
     */
    @Resource
    private Optional<IAccessTokenResponseConverter> oauth2AccessTokenResponseConverters;

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
        OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> delegate = getDelegate(registrationId);
        return delegate.getTokenResponse(authorizationCodeGrantRequest);
    }

    /**
     * 获取委托
     *
     * @param registrationId 注册标识
     * @return 委托
     */
    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getDelegate(String registrationId) {
        DefaultAuthorizationCodeTokenResponseClient delegate = new DefaultAuthorizationCodeTokenResponseClient();
        // 根据注册标识获取授权码授权请求实体转换器
        requestEntityConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(delegate::setRequestEntityConverter);
        // 根据注册标识获取 accessToken 响应转换器
        oauth2AccessTokenResponseConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(converter -> {
                    OAuth2AccessTokenResponseHttpMessageConverter messageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
                    messageConverter.setAccessTokenResponseConverter(converter);
                    RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                            messageConverter,
                            new FormHttpMessageConverter(),
                            new MappingJackson2HttpMessageConverter()
                    ));
                    restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
                    delegate.setRestOperations(restTemplate);
                });
        return delegate;
    }

}
