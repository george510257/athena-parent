package com.athena.security.servlet.client.delegate;

import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.social.SocialUser;
import com.athena.security.servlet.client.social.SocialUserRepository;
import com.athena.starter.web.util.WebUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * 委托 OAuth2 用户信息服务
 *
 * @author george
 */
@Component
public class DelegateOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * 用户请求转换器列表
     */
    @Resource
    private ObjectProvider<IUserRequestConverter> requestConverters;

    /**
     * 用户响应转换器列表
     */
    @Resource
    private ObjectProvider<IUserResponseConverter> responseConverters;
    /**
     * 社交用户仓库
     */
    @Resource
    private SocialUserRepository socialUserRepository;

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
        // 获取委托
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = getDelegate(registrationId);
        // 加载用户
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        // 转换为社交用户
        SocialUser socialUser = convetToSocialUser(oauth2User, registrationId);
        // 未绑定
        if (!socialUser.getBindStatus()) {
            // 设置社交用户
            WebUtil.getSession().ifPresent(session -> session.setAttribute(ClientSecurityConstants.SOCIAL_USER_SESSION_KEY, socialUser));
            // 抛出异常
            throw new OAuth2AuthenticationException(new OAuth2Error("social_user_not_bind", "社交用户未绑定", null));
        }
        // 返回用户
        return socialUser;
    }

    /**
     * 转换为社交用户
     *
     * @param oauth2User     用户
     * @param registrationId 注册 ID
     * @return 社交用户
     */
    private SocialUser convetToSocialUser(OAuth2User oauth2User, String registrationId) {
        SocialUser socialUser = socialUserRepository.loadSocialUser(registrationId, oauth2User.getName());
        if (socialUser == null) {
            socialUser = new SocialUser();
            socialUser.setProviderId(registrationId);
            socialUser.setOauth2User(oauth2User);
            socialUser.setBindStatus(false);
            socialUserRepository.saveSocialUser(socialUser);
        }
        return socialUser;
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
