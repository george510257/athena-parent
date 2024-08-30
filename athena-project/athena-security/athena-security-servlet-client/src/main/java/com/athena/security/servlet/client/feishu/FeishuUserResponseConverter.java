package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IUserResponseConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeishuUserResponseConverter implements IUserResponseConverter {
    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }

    @Override
    public Converter<Map<String, Object>, Map<String, Object>> convert(OAuth2UserRequest source) {
        return this::convert;
    }

    private Map<String, Object> convert(Map<String, Object> params) {
        return (Map<String, Object>) params.get("data");
    }
}
