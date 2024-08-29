package com.athena.security.servlet.client.feishu;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import com.athena.security.servlet.client.base.IMapAccessTokenResponseConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FeishuMapAccessTokenResponseConverter implements IMapAccessTokenResponseConverter {

    private final DefaultMapOAuth2AccessTokenResponseConverter delegate = new DefaultMapOAuth2AccessTokenResponseConverter();

    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> parameters) {
        return delegate.convert(MapUtil.get(parameters, "data", new TypeReference<>() {
        }));
    }

    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }
}
