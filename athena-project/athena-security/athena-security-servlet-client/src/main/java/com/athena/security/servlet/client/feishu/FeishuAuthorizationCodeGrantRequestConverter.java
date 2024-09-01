package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeGrantRequestConverter;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 飞书 OAuth2 授权码请求转换器
 */
@Component
public class FeishuAuthorizationCodeGrantRequestConverter implements IAuthorizationCodeGrantRequestConverter {
    /**
     * 飞书助手
     */
    @Resource
    private FeishuHelper feishuHelper;
    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;

    /**
     * 判断是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        // 判断是否为飞书注册标识
        return feishuProperties.getRegistrationId().equals(registrationId);
    }

    /**
     * 转换为请求实体
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 请求实体
     */
    @Override
    public RequestEntity<?> convert(@NonNull OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        // 请求头
        HttpHeaders headers = this.convertHeaders(authorizationCodeGrantRequest);
        // 请求参数
        Map<String, String> parameters = this.convertParameters(authorizationCodeGrantRequest);
        // 请求地址
        URI uri = URI.create(authorizationCodeGrantRequest.getClientRegistration().getProviderDetails().getTokenUri());
        // 创建请求实体
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
        OAuth2AuthorizationExchange authorizationExchange = authorizationCodeGrantRequest.getAuthorizationExchange();
        // 授权类型
        parameters.put(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
        // 授权码
        parameters.put(OAuth2ParameterNames.CODE, authorizationExchange.getAuthorizationResponse().getCode());
        // 返回参数
        return parameters;
    }

    /**
     * 转换请求头
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 请求头
     */
    private HttpHeaders convertHeaders(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        // 客户端注册
        ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置请求头
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 设置授权头
        headers.setBearerAuth(feishuHelper.getAppAccessToken(clientRegistration.getClientId(), clientRegistration.getClientSecret()));
        // 返回请求头
        return headers;
    }

}
