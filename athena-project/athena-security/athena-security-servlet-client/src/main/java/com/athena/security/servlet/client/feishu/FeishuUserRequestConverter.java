package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.delegate.IUserRequestConverter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.stereotype.Component;

@Component
public class FeishuUserRequestConverter implements IUserRequestConverter {

    private final OAuth2UserRequestEntityConverter converter = new OAuth2UserRequestEntityConverter();

    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }

    @Override
    public RequestEntity<?> convert(OAuth2UserRequest oauth2UserRequest) {
        return converter.convert(oauth2UserRequest);
    }
}
