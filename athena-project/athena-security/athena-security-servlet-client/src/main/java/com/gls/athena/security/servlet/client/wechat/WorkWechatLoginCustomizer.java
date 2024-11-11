package com.gls.athena.security.servlet.client.wechat;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.gls.athena.security.servlet.client.delegate.IOAuth2LoginCustomizer;
import com.gls.athena.security.servlet.client.wechat.domain.*;
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
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 企业微信登录定制器
 *
 * @author george
 */
@Component
public class WorkWechatLoginCustomizer implements IOAuth2LoginCustomizer {
    /**
     * 企业微信属性配置
     */
    @Resource
    private WechatProperties wechatProperties;
    /**
     * 企业微信助手
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
     * 自定义 OAuth2 授权请求
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 企业微信 OAuth2 授权请求参数处理
        builder.parameters(parameters -> {
            // 企业微信 OAuth2 授权请求参数处理
            Map<String, Object> map = new HashMap<>(6);
            map.put("login_type", wechatProperties.getWork().getLoginType());
            map.put("appId", parameters.get(OAuth2ParameterNames.CLIENT_ID));
            map.put("agentid", wechatProperties.getWork().getAgentId());
            map.put("redirect_uri", parameters.get(OAuth2ParameterNames.REDIRECT_URI));
            map.put("state", parameters.get(OAuth2ParameterNames.STATE));
            map.put("lang", wechatProperties.getWork().getLang());
            parameters.clear();
            parameters.putAll(map);
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
        String code = authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode();
        String corpid = authorizationGrantRequest.getClientRegistration().getClientId();
        String corpsecret = authorizationGrantRequest.getClientRegistration().getClientSecret();
        WorkAccessTokenResponse response = wechatHelper.getWorkAccessToken(corpid, corpsecret);
        return convertResponse(response, code);
    }

    /**
     * 转换响应
     *
     * @param response 响应
     * @param code     授权码
     * @return 令牌响应
     */
    private OAuth2AccessTokenResponse convertResponse(WorkAccessTokenResponse response, String code) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .expiresIn(response.getExpiresIn())
                .scopes(wechatProperties.getWork().getScopes())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .additionalParameters(convertAdditionalParameters(code))
                .build();
    }

    /**
     * 转换附加参数
     *
     * @param code 授权码
     * @return 附加参数
     */
    private Map<String, Object> convertAdditionalParameters(String code) {
        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("code", code);
        return additionalParameters;
    }

    /**
     * 加载用户
     *
     * @param userRequest 用户请求
     * @return 用户
     * @throws OAuth2AuthenticationException OAuth2 认证异常
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        WorkUserLoginRequest request = convertUserLoginRequest(userRequest);
        WorkUserLoginResponse response = wechatHelper.getWorkUserLogin(request);
        if (StrUtil.isBlank(response.getUserid())) {
            throw new OAuth2AuthenticationException("获取企业微信用户登录身份失败");
        }
        WorkUserInfoRequest userInfoRequest = convertUserInfoRequest(userRequest, response.getUserid());
        WorkUserInfoResponse userInfoResponse = wechatHelper.getWorkUserInfo(userInfoRequest);
        return convertUser(userInfoResponse, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换请求
     *
     * @param userRequest 用户请求
     * @return 企业微信用户登录请求
     */
    private WorkUserLoginRequest convertUserLoginRequest(OAuth2UserRequest userRequest) {
        WorkUserLoginRequest request = new WorkUserLoginRequest();
        request.setAccessToken(userRequest.getAccessToken().getTokenValue());
        request.setCode(StrUtil.toString(userRequest.getAdditionalParameters().get("code")));
        return request;
    }

    /**
     * 转换用户
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
}
