package com.athena.security.servlet.client.wechat.work;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceAdapter;
import com.athena.security.servlet.client.wechat.WechatHelper;
import com.athena.security.servlet.client.wechat.WechatProperties;
import com.athena.security.servlet.client.wechat.domain.WorkUserInfoRequest;
import com.athena.security.servlet.client.wechat.domain.WorkUserInfoResponse;
import com.athena.security.servlet.client.wechat.domain.WorkUserLoginRequest;
import com.athena.security.servlet.client.wechat.domain.WorkUserLoginResponse;
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
 * 企业微信 OAuth2UserService 定制器
 *
 * @author george
 */
@Component
public class WorkUserServiceAdapter implements IOAuth2UserServiceAdapter {
    /**
     * 企业微信属性配置
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
        return wechatProperties.getWork().getRegistrationId().equals(registrationId);
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
        WorkUserLoginRequest request = convertRequest(userRequest);
        WorkUserLoginResponse response = wechatHelper.getWorkUserLogin(request);
        if (StrUtil.isBlank(response.getUserid())) {
            throw new OAuth2AuthenticationException("获取企业微信用户登录身份失败");
        }
        WorkUserInfoRequest userInfoRequest = convertUserInfoRequest(userRequest, response.getUserid());
        WorkUserInfoResponse userInfoResponse = wechatHelper.getWorkUserInfo(userInfoRequest);
        return convertUser(userInfoResponse, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换用户
     *
     * @param response 微信用户信息响应
     * @param scopes   权限
     * @return OAuth2 用户
     */
    private OAuth2User convertUser(WorkUserInfoResponse response, Set<String> scopes) {
        // 转换为 OAuth2 用户
        Map<String, Object> attributes = BeanUtil.beanToMap(response);
        // 设置权限
        Set<GrantedAuthority> authorities = scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toSet());
        // 返回用户
        return new DefaultOAuth2User(authorities, attributes, "userid");
    }

    /**
     * 转换用户信息请求
     *
     * @param userRequest 用户请求
     * @param userid      用户标识
     * @return 企业微信用户信息请求
     */
    private WorkUserInfoRequest convertUserInfoRequest(OAuth2UserRequest userRequest, String userid) {
        WorkUserInfoRequest request = new WorkUserInfoRequest();
        request.setAccessToken(userRequest.getAccessToken().getTokenValue());
        request.setUserid(userid);
        return request;
    }

    /**
     * 转换请求
     *
     * @param userRequest 用户请求
     * @return 企业微信用户登录请求
     */
    private WorkUserLoginRequest convertRequest(OAuth2UserRequest userRequest) {
        WorkUserLoginRequest request = new WorkUserLoginRequest();
        request.setAccessToken(userRequest.getAccessToken().getTokenValue());
        request.setCode(StrUtil.toString(userRequest.getAdditionalParameters().get("code")));
        return request;
    }
}
