package com.athena.security.servlet.client.feishu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IOAuth2LoginCustomizer;
import com.athena.security.servlet.client.feishu.domian.FeishuUserAccessTokenRequest;
import com.athena.security.servlet.client.feishu.domian.FeishuUserAccessTokenResponse;
import com.athena.security.servlet.client.feishu.domian.FeishuUserInfoResponse;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * 飞书登录定制器
 *
 * @author george
 */
@Component
public class FeishuLoginCustomizer implements IOAuth2LoginCustomizer {

    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;
    /**
     * 飞书助手
     */
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
     * 自定义 OAuth2 授权请求
     *
     * @param builder 构建器
     * @param request 请求
     */
    @Override
    public void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request) {
        // 飞书 OAuth2 授权请求参数处理
        builder.authorizationRequestUri(uriBuilder -> {
            // 获取 URI
            String uri = uriBuilder.build().getQuery();
            // 替换 client_id 为 app_id
            uri = uri.replace(OAuth2ParameterNames.CLIENT_ID, "app_id");
            // 飞书 OAuth2 授权请求参数处理
            return uriBuilder.replaceQuery(uri).build();
        });
    }

    /**
     * 自定义 OAuth2 访问令牌响应
     *
     * @param authorizationGrantRequest 授权码授权请求
     * @return 访问令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        // 获取用户访问令牌
        FeishuUserAccessTokenRequest request = convertAccessTokenRequest(authorizationGrantRequest);
        // 获取客户端标识
        String clientId = authorizationGrantRequest.getClientRegistration().getClientId();
        String clientSecret = authorizationGrantRequest.getClientRegistration().getClientSecret();
        // 获取用户访问令牌响应
        FeishuUserAccessTokenResponse response = feishuHelper.getUserAccessToken(request, clientId, clientSecret);
        // 转换响应
        return convertAccessTokenResponse(response);
    }

    /**
     * 转换访问令牌请求
     *
     * @param authorizationGrantRequest 授权码授权请求
     * @return 访问令牌请求
     */
    private FeishuUserAccessTokenRequest convertAccessTokenRequest(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        FeishuUserAccessTokenRequest request = new FeishuUserAccessTokenRequest();
        request.setCode(authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        request.setGrantType(authorizationGrantRequest.getGrantType().getValue());
        return request;
    }

    /**
     * 转换访问令牌响应
     *
     * @param response 访问令牌响应
     * @return 访问令牌响应
     */
    private OAuth2AccessTokenResponse convertAccessTokenResponse(FeishuUserAccessTokenResponse response) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(response.getExpiresIn())
                .refreshToken(response.getRefreshToken())
                .scopes(convertScopes(response.getScope()))
                .additionalParameters(convertAdditionalParameters(response))
                .build();
    }

    /**
     * 转换权限
     *
     * @param scope 权限
     * @return 权限
     */
    private Set<String> convertScopes(String scope) {
        return StrUtil.isBlank(scope) ? Collections.emptySet() : new HashSet<>(StrUtil.split(scope, " "));
    }

    /**
     * 转换附加参数
     *
     * @param response 访问令牌响应
     * @return 附加参数
     */
    private Map<String, Object> convertAdditionalParameters(FeishuUserAccessTokenResponse response) {
        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("refreshTokenExpiresIn", response.getRefreshTokenExpiresIn());
        return additionalParameters;
    }

    /**
     * 自定义 OAuth2 用户信息服务
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
     * @param response 用户信息响应
     * @param scopes   权限
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
