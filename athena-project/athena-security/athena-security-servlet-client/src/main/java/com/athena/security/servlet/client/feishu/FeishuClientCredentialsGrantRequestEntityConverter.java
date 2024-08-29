package com.athena.security.servlet.client.feishu;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;

public class FeishuClientCredentialsGrantRequestEntityConverter implements Converter<OAuth2ClientCredentialsGrantRequest, RequestEntity<?>> {
    @Override
    public RequestEntity<?> convert(OAuth2ClientCredentialsGrantRequest source) {
        return null;
    }
}
