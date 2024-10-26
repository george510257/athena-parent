package com.athena.security.servlet.client.wechat.open;

import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientAdapter;
import com.athena.security.servlet.client.wechat.WechatHelper;
import com.athena.security.servlet.client.wechat.WechatProperties;
import com.athena.security.servlet.client.wechat.domain.WechatAccessTokenRequest;
import com.athena.security.servlet.client.wechat.domain.WechatAccessTokenResponse;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 微信授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class WechatAuthorizationCodeTokenResponseClientAdapter implements IAuthorizationCodeTokenResponseClientAdapter {
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
     * 获取令牌响应
     *
     * @param authorizationGrantRequest 请求
     * @return 令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        WechatAccessTokenRequest request = this.convertRequest(authorizationGrantRequest);
        WechatAccessTokenResponse response = wechatHelper.getAccessToken(request);
        return convertResponse(response);
    }

    /**
     * 转换响应
     *
     * @param response 响应
     * @return 令牌响应
     */
    private OAuth2AccessTokenResponse convertResponse(WechatAccessTokenResponse response) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .expiresIn(response.getExpiresIn())
                .refreshToken(response.getRefreshToken())
                .scopes(convertScopes(response.getScope()))
                .additionalParameters(convertAdditionalParameters(response))
                .build();
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
     * 转换权限集合
     *
     * @param scope 权限
     * @return 权限集合
     */
    private Set<String> convertScopes(String scope) {
        return StrUtil.isBlank(scope) ? Collections.emptySet() : new HashSet<>(StrUtil.split(scope, ","));
    }

    /**
     * 转换请求
     *
     * @param authorizationGrantRequest 请求
     * @return 请求
     */
    private WechatAccessTokenRequest convertRequest(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        WechatAccessTokenRequest request = new WechatAccessTokenRequest();
        request.setAppid(authorizationGrantRequest.getClientRegistration().getClientId());
        request.setSecret(authorizationGrantRequest.getClientRegistration().getClientSecret());
        request.setCode(authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        request.setGrantType(authorizationGrantRequest.getGrantType().getValue());
        return request;
    }
}
