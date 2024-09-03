package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeGrantRequestConverter;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信授权码请求转换器
 *
 * @author george
 */
@Component
public class WechatAuthorizationCodeGrantRequestConverter implements IAuthorizationCodeGrantRequestConverter {
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMpRegistrationId().equals(registrationId)
                || wechatProperties.getOpenRegistrationId().equals(registrationId);
    }

    /**
     * 转换为请求实体
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 请求实体
     */
    @Override
    public RequestEntity<?> convert(@NonNull OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(authorizationCodeGrantRequest);
        // 请求地址
        URI uri = UriComponentsBuilder.fromUriString(authorizationCodeGrantRequest.getClientRegistration().getProviderDetails().getTokenUri())
                .queryParams(parameters)
                .build().toUri();
        // 创建请求实体
        return RequestEntity.get(uri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * 转换为请求参数
     *
     * @param authorizationCodeGrantRequest 授权码授权请求
     * @return 请求参数
     */
    private MultiValueMap<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("appid", authorizationCodeGrantRequest.getClientRegistration().getClientId());
        parameters.add("secret", authorizationCodeGrantRequest.getClientRegistration().getClientSecret());
        parameters.add(OAuth2ParameterNames.CODE, authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        parameters.add(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
        return parameters;
    }
}
