package com.athena.security.servlet.client.wechat.mini;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientAdapter;
import com.athena.security.servlet.client.wechat.WechatHelper;
import com.athena.security.servlet.client.wechat.WechatProperties;
import com.athena.security.servlet.client.wechat.domain.MiniAppAccessTokenResponse;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 小程序授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class MiniAppAuthorizationCodeTokenResponseClientAdapter implements IAuthorizationCodeTokenResponseClientAdapter {
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
     * 获取令牌响应
     *
     * @param authorizationGrantRequest 授权码授权请求
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
     * 转换响应
     *
     * @param response 响应
     * @param code     授权码
     * @return 令牌响应
     */
    private OAuth2AccessTokenResponse convertResponse(MiniAppAccessTokenResponse response, String code) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .expiresIn(response.getExpiresIn())
                .scopes(Set.of("mini_wechat_user"))
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
}
