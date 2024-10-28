package com.athena.security.servlet.client.wechat;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2LoginCustomizer;
import com.athena.security.servlet.client.wechat.domain.MiniAppAccessTokenResponse;
import com.athena.security.servlet.client.wechat.domain.MiniAppLoginRequest;
import com.athena.security.servlet.client.wechat.domain.MiniAppLoginResponse;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 小程序登录定制器
 *
 * @author george
 */
@Component
public class MiniAppLoginCustomizer implements IOAuth2LoginCustomizer {
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;
    /**
     * 小程序助手
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
     * 自定义 OAuth2 授权请求
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 小程序 OAuth2 授权请求参数处理
        builder.parameters(parameters -> {
            Map<String, Object> result = new HashMap<>(2);
            result.put("code", request.getParameter("code"));
            result.put("state", parameters.get("state"));
            parameters.clear();
            parameters.putAll(result);
        });
    }

    /**
     * 获取令牌响应
     *
     * @param authorizationGrantRequest 授权请求
     * @return 令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        String code = authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode();
        String appId = authorizationGrantRequest.getClientRegistration().getClientId();
        String appSecret = authorizationGrantRequest.getClientRegistration().getClientSecret();
        MiniAppAccessTokenResponse response = wechatHelper.getMiniAppAccessToken(appId, appSecret);
        return convertResponse(response, code);
    }

    /**
     * 转换响应 令牌响应
     *
     * @param response 响应
     * @param code     授权码
     * @return 令牌响应
     */
    private OAuth2AccessTokenResponse convertResponse(MiniAppAccessTokenResponse response, String code) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .expiresIn(response.getExpiresIn())
                .scopes(wechatProperties.getMiniApp().getScopes())
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
        additionalParameters.put("js_code", code);
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
        MiniAppLoginRequest request = convertMiniAppLoginRequest(userRequest);
        MiniAppLoginResponse response = wechatHelper.getMiniAppLogin(request);
        return convertMiniAppLoginResponse(response, userRequest.getAccessToken().getScopes());
    }

    /**
     * 转换小程序登录请求
     *
     * @param userRequest 用户请求
     * @return 小程序登录请求
     */
    private MiniAppLoginRequest convertMiniAppLoginRequest(OAuth2UserRequest userRequest) {
        MiniAppLoginRequest request = new MiniAppLoginRequest();
        request.setAppId(userRequest.getClientRegistration().getClientId());
        request.setSecret(userRequest.getClientRegistration().getClientSecret());
        request.setJsCode(StrUtil.toString(userRequest.getAdditionalParameters().get("js_code")));
        return request;
    }

    /**
     * 转换小程序登录响应
     *
     * @param response 响应
     * @param scopes   权限
     * @return OAuth2 用户
     */
    private OAuth2User convertMiniAppLoginResponse(MiniAppLoginResponse response, Set<String> scopes) {
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
