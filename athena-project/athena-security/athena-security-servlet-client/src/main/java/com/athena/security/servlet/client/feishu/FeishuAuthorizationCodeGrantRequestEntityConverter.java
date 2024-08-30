package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.base.IAuthorizationCodeGrantRequestEntityConverter;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 飞书 OAuth2 授权码请求转换器
 */
@Component
public class FeishuAuthorizationCodeGrantRequestEntityConverter implements IAuthorizationCodeGrantRequestEntityConverter {
    /**
     * OAuth2 授权码请求转换器
     */
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter converter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
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
        converter.setHeadersConverter(this::convertHeaders);
        converter.setParametersConverter(this::convertParameters);
        RequestEntity<?> requestEntity = converter.convert(authorizationCodeGrantRequest);
        MultiValueMap<String, String> body = (MultiValueMap<String, String>) requestEntity.getBody();
        return RequestEntity.post(requestEntity.getUrl())
                .headers(requestEntity.getHeaders())
                .body(body.toSingleValueMap());
    }

    /**
     * 转换参数
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 参数
     */
    private MultiValueMap<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("code", authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
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
