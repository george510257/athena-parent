package com.athena.security.servlet.client.delegate;

import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 委托 OAuth2 用户信息服务
 */
@Component
public class DelegateOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    /**
     * 用户请求转换器列表
     */
    @Resource
    private List<IUserRequestConverter> requestConverters;

    /**
     * 用户响应转换器列表
     */
    @Resource
    private List<IUserResponseConverter> responseConverters;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 获取注册 ID
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        requestConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(delegate::setRequestEntityConverter);
        // 根据注册 ID 获取用户响应转换器
        responseConverters.stream()
                .filter(converter -> converter.test(registrationId))
                .findFirst()
                .ifPresent(delegate::setAttributesConverter);
        // 加载用户
        return delegate.loadUser(userRequest);
    }
}
