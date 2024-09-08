package com.athena.security.servlet.client.wechat;

import cn.hutool.core.collection.CollUtil;
import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientCustomizer;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class WechatAuthorizationCodeTokenResponseClientCustomizer implements IAuthorizationCodeTokenResponseClientCustomizer {
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
        return wechatProperties.getMp().getRegistrationId().equals(registrationId)
                || wechatProperties.getOpen().getRegistrationId().equals(registrationId);
    }

    /**
     * 定制化
     *
     * @param client 授权码令牌响应客户端
     */
    @Override
    public void customize(DefaultAuthorizationCodeTokenResponseClient client) {
        // 设置请求实体转换器
        client.setRequestEntityConverter(this::requestEntityConverter);
    }

    /**
     * 请求实体转换器
     *
     * @param request 授权码授权请求
     * @return 请求实体
     */
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

    /**
     * 请求参数转换
     *
     * @param request 授权码授权请求
     * @return 请求参数
     */
    private MultiValueMap<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(ClientSecurityConstants.WECHAT_APP_ID, request.getClientRegistration().getClientId());
        parameters.add(ClientSecurityConstants.WECHAT_APP_SECRET, request.getClientRegistration().getClientSecret());
        parameters.add(OAuth2ParameterNames.CODE, request.getAuthorizationExchange().getAuthorizationResponse().getCode());
        parameters.add(OAuth2ParameterNames.GRANT_TYPE, request.getGrantType().getValue());
        return parameters;
    }
}