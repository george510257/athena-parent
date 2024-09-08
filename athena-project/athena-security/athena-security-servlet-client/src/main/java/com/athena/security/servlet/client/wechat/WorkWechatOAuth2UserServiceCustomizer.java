package com.athena.security.servlet.client.wechat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceCustomizer;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 企业微信 OAuth2UserService 定制器
 */
@Component
public class WorkWechatOAuth2UserServiceCustomizer implements IOAuth2UserServiceCustomizer {
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getWork().getRegistrationId().equals(registrationId);
    }

    @Override
    public void customize(DefaultOAuth2UserService service) {
        // 设置属性转换器
        service.setRequestEntityConverter(this::requestEntityConverter);
    }

    private RequestEntity<?> requestEntityConverter(OAuth2UserRequest request) {
        // 请求头
        HttpHeaders headers = this.convertHeaders(request);
        // 请求参数
        MultiValueMap<String, String> parameters = this.convertParameters(request);
        // 请求 URI
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUri())
                .queryParams(parameters)
                .build().toUri();
        return RequestEntity.get(uri)
                .headers(headers)
                .build();
    }

    private HttpHeaders convertHeaders(OAuth2UserRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(CollUtil.toList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private MultiValueMap<String, String> convertParameters(OAuth2UserRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(OAuth2ParameterNames.ACCESS_TOKEN, request.getAccessToken().getTokenValue());
        parameters.add(OAuth2ParameterNames.CODE, StrUtil.toString(request.getAdditionalParameters().get(OAuth2ParameterNames.CODE)));
        return parameters;
    }
}
