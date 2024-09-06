package com.athena.security.servlet.client.wechat;

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
 * 微信 OAuth2UserService 定制器
 *
 * @author george
 */
@Component
public class WechatOAuth2UserServiceCustomizer implements IOAuth2UserServiceCustomizer {
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMpRegistrationId().equals(registrationId)
                || wechatProperties.getOpenRegistrationId().equals(registrationId);
    }

    @Override
    public void customize(DefaultOAuth2UserService oAuth2UserService) {
        // 设置属性转换器
        oAuth2UserService.setRequestEntityConverter(this::requestEntityConverter);
    }

    private RequestEntity<?> requestEntityConverter(OAuth2UserRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(OAuth2ParameterNames.ACCESS_TOKEN, request.getAccessToken().getTokenValue());
        parameters.add("openid", StrUtil.toString(request.getAdditionalParameters().get("openid")));
        parameters.add("lang", "zh_CN");
        URI uri = UriComponentsBuilder.fromUriString(request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())
                .queryParams(parameters)
                .build().toUri();
        return RequestEntity.get(uri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
