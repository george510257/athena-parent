package com.athena.security.servlet.client.wechat.work;

import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientAdapter;
import com.athena.security.servlet.client.wechat.WechatHelper;
import com.athena.security.servlet.client.wechat.WechatProperties;
import com.athena.security.servlet.client.wechat.domain.WorkAccessTokenResponse;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 企业微信授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class WorkAuthorizationCodeTokenResponseClientAdapter implements IAuthorizationCodeTokenResponseClientAdapter {
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
                .scopes(Set.of("work_wechat_user"))
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

}
