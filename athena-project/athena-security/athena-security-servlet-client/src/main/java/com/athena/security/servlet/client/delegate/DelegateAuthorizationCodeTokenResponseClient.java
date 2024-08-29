package com.athena.security.servlet.client.delegate;

import com.athena.security.servlet.client.base.IAuthorizationCodeGrantRequestEntityConverter;
import com.athena.security.servlet.client.base.IMapAccessTokenResponseConverter;
import jakarta.annotation.Resource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DelegateAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final DefaultAuthorizationCodeTokenResponseClient delegate = new DefaultAuthorizationCodeTokenResponseClient();

    @Resource
    private List<IAuthorizationCodeGrantRequestEntityConverter> requestEntityConverters;

    @Resource
    private List<IMapAccessTokenResponseConverter> oauth2AccessTokenResponseConverters;

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        String registrationId = authorizationCodeGrantRequest.getClientRegistration().getRegistrationId();
        requestEntityConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(this.delegate::setRequestEntityConverter);
        oauth2AccessTokenResponseConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(converter -> {
                    RestTemplate restTemplate = new RestTemplate();
                    OAuth2AccessTokenResponseHttpMessageConverter messageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
                    messageConverter.setAccessTokenResponseConverter(converter);
                    restTemplate.setMessageConverters(List.of(new FormHttpMessageConverter(), messageConverter));
                    restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
                    this.delegate.setRestOperations(restTemplate);
                });
        return this.delegate.getTokenResponse(authorizationCodeGrantRequest);
    }

}
