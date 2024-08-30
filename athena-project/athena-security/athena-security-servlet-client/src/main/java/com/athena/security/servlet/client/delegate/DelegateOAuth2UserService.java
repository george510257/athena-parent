package com.athena.security.servlet.client.delegate;

import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DelegateOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Resource
    private List<IUserRequestConverter> requestConverters;

    @Resource
    private List<IUserResponseConverter> responseConverters;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        requestConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(delegate::setRequestEntityConverter);
        responseConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(delegate::setAttributesConverter);
        return delegate.loadUser(userRequest);
    }
}
