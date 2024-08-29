package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.base.IAuthorizationCodeGrantRequestEntityConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

/**
 * 飞书 OAuth2 授权码请求转换器
 */
@Component
public class FeishuAuthorizationCodeGrantRequestEntityConverter implements IAuthorizationCodeGrantRequestEntityConverter {

    private final OAuth2AuthorizationCodeGrantRequestEntityConverter converter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();

    @Override
    public boolean test(String registrationId) {
        return "feishu".equals(registrationId);
    }

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        converter.addHeadersConverter(this::convertHeaders);
        return converter.convert(authorizationCodeGrantRequest);
    }

    private HttpHeaders convertHeaders(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.setBearerAuth(getAppAccessToken(clientRegistration));
        return headers;
    }

    private String getAppAccessToken(ClientRegistration clientRegistration) {
        DefaultClientCredentialsTokenResponseClient clientCredentialsTokenResponseClient = new DefaultClientCredentialsTokenResponseClient();
        clientCredentialsTokenResponseClient.setRequestEntityConverter(new FeishuClientCredentialsGrantRequestEntityConverter());
        OAuth2ClientCredentialsGrantRequest clientCredentialsGrantRequest = new OAuth2ClientCredentialsGrantRequest(clientRegistration);
        return clientCredentialsTokenResponseClient.getTokenResponse(clientCredentialsGrantRequest).getAccessToken().getTokenValue();
    }
}
