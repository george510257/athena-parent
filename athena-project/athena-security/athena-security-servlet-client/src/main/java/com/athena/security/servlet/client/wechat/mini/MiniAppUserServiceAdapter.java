package com.athena.security.servlet.client.wechat.mini;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceAdapter;
import com.athena.security.servlet.client.wechat.WechatHelper;
import com.athena.security.servlet.client.wechat.WechatProperties;
import com.athena.security.servlet.client.wechat.domain.MiniAppLoginRequest;
import com.athena.security.servlet.client.wechat.domain.MiniAppLoginResponse;
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
 * 小程序用户服务适配器
 *
 * @author george
 */
@Component
public class MiniAppUserServiceAdapter implements IOAuth2UserServiceAdapter {
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
     * 测试是否支持
     *
     * @param registrationId 客户端注册 ID
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return wechatProperties.getMiniApp().getRegistrationId().equals(registrationId);
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
        MiniAppLoginRequest request = convertRequest(userRequest);
        MiniAppLoginResponse response = wechatHelper.getMiniAppLogin(request);
        return convertResponse(response, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换响应
     *
     * @param response 响应
     * @param scopes   权限
     * @return OAuth2 用户
     */
    private OAuth2User convertResponse(MiniAppLoginResponse response, Set<String> scopes) {
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
     * @return 小程序登录请求
     */
    private MiniAppLoginRequest convertRequest(OAuth2UserRequest userRequest) {
        MiniAppLoginRequest request = new MiniAppLoginRequest();
        request.setAppId(userRequest.getClientRegistration().getClientId());
        request.setSecret(userRequest.getClientRegistration().getClientSecret());
        request.setJsCode(StrUtil.toString(userRequest.getAdditionalParameters().get("js_code")));
        return request;
    }
}
