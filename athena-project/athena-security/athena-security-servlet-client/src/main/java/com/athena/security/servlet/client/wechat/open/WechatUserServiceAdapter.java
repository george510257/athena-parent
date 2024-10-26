package com.athena.security.servlet.client.wechat.open;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceAdapter;
import com.athena.security.servlet.client.wechat.WechatHelper;
import com.athena.security.servlet.client.wechat.WechatProperties;
import com.athena.security.servlet.client.wechat.domain.WechatUserInfoRequest;
import com.athena.security.servlet.client.wechat.domain.WechatUserInfoResponse;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 微信 OAuth2UserService 定制器
 *
 * @author george
 */
@Component
public class WechatUserServiceAdapter implements IOAuth2UserServiceAdapter {
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;
    /**
     * 微信助手
     */
    @Resource
    private WechatHelper wechatHelper;

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMp().getRegistrationId().equals(registrationId)
                || wechatProperties.getOpen().getRegistrationId().equals(registrationId);
    }

    /**
     * 加载用户
     *
     * @param userRequest 用户请求
     * @return OAuth2 用户
     * @throws OAuth2AuthenticationException OAuth2 认证异常
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        WechatUserInfoRequest request = this.convertRequest(userRequest);
        WechatUserInfoResponse response = wechatHelper.getUserInfo(request);
        return convertUser(response, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换用户
     *
     * @param response 微信用户信息响应
     * @param scopes   权限
     * @return OAuth2 用户
     */
    private OAuth2User convertUser(WechatUserInfoResponse response, Set<String> scopes) {
        // 转换为 OAuth2 用户
        Map<String, Object> attributes = BeanUtil.beanToMap(response);
        // 设置权限
        Set<GrantedAuthority> authorities = scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toSet());
        // 返回用户
        return new DefaultOAuth2User(authorities, attributes, "unionid");
    }

    /**
     * 转换请求
     *
     * @param userRequest 用户请求
     * @return 微信用户信息请求
     */
    private WechatUserInfoRequest convertRequest(OAuth2UserRequest userRequest) {
        WechatUserInfoRequest request = new WechatUserInfoRequest();
        request.setAccessToken(userRequest.getAccessToken().getTokenValue());
        request.setOpenid(StrUtil.toString(userRequest.getAdditionalParameters().get("openid")));
        request.setLang(wechatProperties.getLang());
        return request;
    }
}
