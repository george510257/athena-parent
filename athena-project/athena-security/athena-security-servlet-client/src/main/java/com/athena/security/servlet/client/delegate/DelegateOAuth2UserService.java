package com.athena.security.servlet.client.delegate;

import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 委托 OAuth2 用户信息服务
 */
@Component
public class DelegateOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    /**
     * 用户请求转换器列表
     */
    @Resource
    private Optional<IUserRequestConverter> requestConverters;

    /**
     * 用户响应转换器列表
     */
    @Resource
    private Optional<IUserResponseConverter> responseConverters;

    /**
     * 加载用户
     *
     * @param userRequest 用户请求
     * @return 用户
     * @throws OAuth2AuthenticationException OAuth2 认证异常
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 获取注册 ID
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = getDelegate(registrationId);
        return delegate.loadUser(userRequest);
    }

    /**
     * 获取委托
     *
     * @param registrationId 注册 ID
     * @return 委托
     */
    private OAuth2UserService<OAuth2UserRequest, OAuth2User> getDelegate(String registrationId) {
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
        return delegate;
    }
}
