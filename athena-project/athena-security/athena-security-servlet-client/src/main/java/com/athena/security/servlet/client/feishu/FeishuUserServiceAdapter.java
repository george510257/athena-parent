package com.athena.security.servlet.client.feishu;

import cn.hutool.core.bean.BeanUtil;
import com.athena.security.servlet.client.delegate.IOAuth2UserServiceAdapter;
import com.athena.security.servlet.client.feishu.domian.FeishuUserInfoResponse;
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
 * 飞书 OAuth2 用户信息服务定制器
 *
 * @author George
 */
@Component
public class FeishuUserServiceAdapter implements IOAuth2UserServiceAdapter {
    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;
    @Resource
    private FeishuHelper feishuHelper;

    /**
     * 测试是否支持指定的注册标识
     *
     * @param registrationId 注册标识
     * @return 是否支持
     */
    @Override
    public boolean test(String registrationId) {
        return feishuProperties.getRegistrationId().equals(registrationId);
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
        String accessToken = userRequest.getAccessToken().getTokenValue();
        FeishuUserInfoResponse response = feishuHelper.getUserInfo(accessToken);
        return convertUser(response, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换用户
     *
     * @param response 飞书用户信息响应
     * @param scopes   权限集合
     * @return OAuth2 用户
     */
    private OAuth2User convertUser(FeishuUserInfoResponse response, Set<String> scopes) {
        // 转换为 OAuth2 用户
        Map<String, Object> attributes = BeanUtil.beanToMap(response);
        // 设置权限
        Set<GrantedAuthority> authorities = scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toSet());
        // 返回用户
        return new DefaultOAuth2User(authorities, attributes, "openId");
    }
}
