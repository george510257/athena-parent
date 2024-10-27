package com.athena.security.servlet.client.wechat;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2LoginCustomizer;
import com.athena.security.servlet.client.wechat.domain.WechatAccessTokenRequest;
import com.athena.security.servlet.client.wechat.domain.WechatAccessTokenResponse;
import com.athena.security.servlet.client.wechat.domain.WechatUserInfoRequest;
import com.athena.security.servlet.client.wechat.domain.WechatUserInfoResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 微信登录定制器
 *
 * @author george
 */
@Component
public class WechatLoginCustomizer implements IOAuth2LoginCustomizer {
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
     * 自定义 OAuth2 授权请求
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 微信 OAuth2 授权请求参数处理
        builder.authorizationRequestUri(uriBuilder -> {
            // 构建 URI
            String uri = uriBuilder.build().getQuery();
            // 替换 client_id 为 appid
            uri = uri.replace("client_id", "appId");
            // 微信 OAuth2 授权请求参数添加 #wechat_redirect
            return uriBuilder.replaceQuery(uri).fragment("wechat_redirect").build();
        });
    }

    /**
     * 获取令牌响应
     *
     * @param authorizationGrantRequest 请求
     * @return 令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        WechatAccessTokenRequest request = convertAccessTokenRequest(authorizationGrantRequest);
        WechatAccessTokenResponse response = wechatHelper.getWechatAccessToken(request);
        return convertAccessTokenResponse(response);
    }

    /**
     * 转换请求
     *
     * @param authorizationGrantRequest 请求
     * @return 微信访问令牌请求
     */
    private WechatAccessTokenRequest convertAccessTokenRequest(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        WechatAccessTokenRequest request = new WechatAccessTokenRequest();
        request.setAppid(authorizationGrantRequest.getClientRegistration().getClientId());
        request.setSecret(authorizationGrantRequest.getClientRegistration().getClientSecret());
        request.setCode(authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        request.setGrantType(authorizationGrantRequest.getGrantType().getValue());
        return request;
    }

    /**
     * 转换响应
     *
     * @param response 响应
     * @return 令牌响应
     */
    private OAuth2AccessTokenResponse convertAccessTokenResponse(WechatAccessTokenResponse response) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .expiresIn(response.getExpiresIn())
                .refreshToken(response.getRefreshToken())
                .scopes(convertScopes(response.getScope()))
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .additionalParameters(convertAdditionalParameters(response))
                .build();
    }

    /**
     * 转换权限集合
     *
     * @param scope 权限
     * @return 权限集合
     */
    private Set<String> convertScopes(String scope) {
        return StrUtil.isBlank(scope) ? Collections.emptySet() : new HashSet<>(StrUtil.split(scope, ","));
    }

    /**
     * 转换附加参数
     *
     * @param response 响应
     * @return 附加参数
     */
    private Map<String, Object> convertAdditionalParameters(WechatAccessTokenResponse response) {
        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("openid", response.getOpenid());
        additionalParameters.put("unionid", response.getUnionid());
        return additionalParameters;
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
        WechatUserInfoRequest request = convertUserInfoRequest(userRequest);
        WechatUserInfoResponse response = wechatHelper.getWechatUserInfo(request);
        return convertUserInfoResponse(response, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换请求
     *
     * @param userRequest 用户请求
     * @return 微信用户信息请求
     */
    private WechatUserInfoRequest convertUserInfoRequest(OAuth2UserRequest userRequest) {
        WechatUserInfoRequest request = new WechatUserInfoRequest();
        request.setAccessToken(userRequest.getAccessToken().getTokenValue());
        request.setOpenid(StrUtil.toString(userRequest.getAdditionalParameters().get("openid")));
        request.setLang(wechatProperties.getLang());
        return request;
    }

    /**
     * 转换用户
     *
     * @param response 微信用户信息响应
     * @param scopes   权限
     * @return OAuth2 用户
     */
    private OAuth2User convertUserInfoResponse(WechatUserInfoResponse response, Set<String> scopes) {
        // 转换为 OAuth2 用户
        Map<String, Object> attributes = BeanUtil.beanToMap(response);
        // 设置权限
        Set<GrantedAuthority> authorities = scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toSet());
        // 返回用户
        return new DefaultOAuth2User(authorities, attributes, "unionid");
    }
}
