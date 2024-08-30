package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeGrantRequestConverter;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 飞书 OAuth2 授权码请求转换器
 */
@Component
public class FeishuAuthorizationCodeGrantRequestConverter implements IAuthorizationCodeGrantRequestConverter {

    @Resource
    private FeishuHelper feishuHelper;

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
        HttpHeaders headers = this.convertHeaders(authorizationCodeGrantRequest);
        Map<String, String> parameters = this.convertParameters(authorizationCodeGrantRequest);
        URI uri = URI.create(authorizationCodeGrantRequest.getClientRegistration().getProviderDetails().getTokenUri());
        return RequestEntity.post(uri)
                .headers(headers)
                .body(parameters);
    }

    /**
     * 转换参数
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 参数
     */
    private Map<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("code", authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        return parameters;
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
        headers.setBearerAuth(feishuHelper.getAppAccessToken(clientRegistration.getClientId(), clientRegistration.getClientSecret()));
        return headers;
    }

}
