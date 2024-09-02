package com.athena.security.servlet.client.weixin;

import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IUserRequestConverter;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信用户请求转换器
 *
 * @author george
 */
@Component
public class WeixinUserRequestConverter implements IUserRequestConverter {

    @Resource
    private WeixinProperties weixinProperties;

    @Override
    public boolean test(String registrationId) {
        return weixinProperties.getMpRegistrationId().equals(registrationId)
                || weixinProperties.getOpenRegistrationId().equals(registrationId);
    }

    @Override
    public RequestEntity<?> convert(@NonNull OAuth2UserRequest oauth2UserRequest) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(OAuth2ParameterNames.ACCESS_TOKEN, oauth2UserRequest.getAccessToken().getTokenValue());
        parameters.add("openid", StrUtil.toString(oauth2UserRequest.getAdditionalParameters().get("openid")));
        parameters.add("lang", "zh_CN");
        URI uri = UriComponentsBuilder.fromUriString(oauth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())
                .queryParams(parameters)
                .build().toUri();
        return RequestEntity.get(uri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
