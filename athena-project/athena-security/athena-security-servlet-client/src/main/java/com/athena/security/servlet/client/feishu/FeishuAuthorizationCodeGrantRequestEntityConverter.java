package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.base.IAuthorizationCodeGrantRequestEntityConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

/**
 * 飞书 OAuth2 授权码请求转换器
 */
@Component
public class FeishuAuthorizationCodeGrantRequestEntityConverter implements IAuthorizationCodeGrantRequestEntityConverter {
    /**
     * OAuth2 授权码请求转换器
     */
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter converter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();

    /**
     * 判断是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }

    /**
     * 转换为请求实体
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 请求实体
     */
    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        converter.addHeadersConverter(this::convertHeaders);
        return converter.convert(authorizationCodeGrantRequest);
    }

    /**
     * 转换请求头
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 请求头
     */
    private HttpHeaders convertHeaders(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.setBearerAuth(getAppAccessToken(clientRegistration.getClientId(), clientRegistration.getClientSecret()));
        return headers;
    }

    /**
     * 获取应用访问令牌
     *
     * @param clientId     客户端标识
     * @param clientSecret 客户端密钥
     * @return 应用访问令牌
     */
    private String getAppAccessToken(String clientId, String clientSecret) {
        // TODO: 获取 app_access_token
        return "";
    }

}
