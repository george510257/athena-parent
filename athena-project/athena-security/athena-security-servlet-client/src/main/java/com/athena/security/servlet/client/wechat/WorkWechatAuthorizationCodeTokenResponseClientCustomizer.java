package com.athena.security.servlet.client.wechat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientCustomizer;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

/**
 * 企业微信授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class WorkWechatAuthorizationCodeTokenResponseClientCustomizer implements IAuthorizationCodeTokenResponseClientCustomizer {
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getWork().getRegistrationId().equals(registrationId);
    }

    @Override
    public void customize(DefaultAuthorizationCodeTokenResponseClient client) {
        client.setRequestEntityConverter(this::requestEntityConverter);
    }

    @Override
    public OAuth2AccessTokenResponse customResponse(OAuth2AuthorizationCodeGrantRequest request, OAuth2AccessTokenResponse response) {
        return OAuth2AccessTokenResponse.withResponse(response)
                .additionalParameters(MapUtil.builder(new HashMap<String, Object>())
                        .put(OAuth2ParameterNames.CODE, request.getAuthorizationExchange().getAuthorizationResponse().getCode())
                        .build())
                .build();
    }

    private RequestEntity<?> requestEntityConverter(OAuth2AuthorizationCodeGrantRequest request) {
        // 请求头
        HttpHeaders headers = this.convertHeaders(request);
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(request);
        // 请求地址
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration().getProviderDetails().getTokenUri())
                .queryParams(parameters)
                .build().toUri();
        // 创建请求实体
        return RequestEntity.get(uri)
                .headers(headers)
                .build();
    }

    private HttpHeaders convertHeaders(OAuth2AuthorizationCodeGrantRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(CollUtil.toList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private MultiValueMap<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest request) {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        ClientRegistration clientRegistration = request.getClientRegistration();
        queryParameters.add(ClientSecurityConstants.WECHAT_WORK_CORP_ID, clientRegistration.getClientId());
        queryParameters.add(ClientSecurityConstants.WECHAT_WORK_CORP_SECRET, clientRegistration.getClientSecret());
        return queryParameters;
    }
}
