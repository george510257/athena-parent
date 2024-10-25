package com.athena.security.servlet.client.delegate;

import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.social.ISocialUserService;
import com.athena.security.servlet.client.social.SocialUser;
import com.athena.security.servlet.client.support.DefaultOAuth2ClientPropertiesMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * 委托 OAuth2 用户信息服务
 *
 * @author george
 */
@Component
public class DelegateOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    /**
     * 默认 OAuth2 用户信息服务
     */
    private static final DefaultOAuth2UserService DEFAULT = new DefaultOAuth2UserService();
    /**
     * 社交用户仓库
     */
    @Resource
    private ISocialUserService socialUserService;
    /**
     * OAuth2UserService 定制器提供者
     */
    @Resource
    private ObjectProvider<IOAuth2UserServiceAdapter> adapters;
    /**
     * 默认 OAuth2 客户端属性映射器
     */
    @Resource
    private DefaultOAuth2ClientPropertiesMapper mapper;
    /**
     * 会话
     */
    @Resource
    private HttpSession session;

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
        // 获取提供者
        String provider = mapper.getProvider(registrationId);
        // 加载用户
        OAuth2User oauth2User = adapters.stream().filter(adapter -> adapter.test(provider)).findFirst()
                .map(adapter -> adapter.loadUser(userRequest))
                .orElseGet(() -> DEFAULT.loadUser(userRequest));
        // 转换为社交用户
        SocialUser socialUser = convetToSocialUser(oauth2User, registrationId);
        // 未绑定
        if (!socialUser.getBindStatus()) {
            // 设置社交用户
            session.setAttribute(ClientSecurityConstants.SOCIAL_USER_SESSION_KEY, socialUser);
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
        SocialUser socialUser = socialUserService.loadSocialUser(registrationId, oauth2User.getName());
        if (socialUser == null) {
            socialUser = new SocialUser();
            socialUser.setProviderId(registrationId);
            socialUser.setBindStatus(false);
            socialUser.setAttributes(oauth2User.getAttributes());
            socialUser.setAuthorities(new HashSet<>(oauth2User.getAuthorities()));
            socialUser.setName(oauth2User.getName());
            socialUserService.saveSocialUser(socialUser);
        }
        return socialUser;
    }

}
