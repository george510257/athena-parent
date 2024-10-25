package com.athena.security.servlet.client.feishu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.client.delegate.IAuthorizationCodeTokenResponseClientAdapter;
import com.athena.security.servlet.client.feishu.domian.FeishuUserAccessTokenRequest;
import com.athena.security.servlet.client.feishu.domian.FeishuUserAccessTokenResponse;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 飞书授权码令牌响应客户端定制器
 *
 * @author george
 */
@Component
public class FeishuAuthorizationCodeTokenResponseClientAdapter implements IAuthorizationCodeTokenResponseClientAdapter {
    /**
     * 飞书助手
     */
    @Resource
    private FeishuHelper feishuHelper;
    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;

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
     * 获取令牌响应
     *
     * @param authorizationGrantRequest 请求
     * @return 令牌响应
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        // 获取用户访问令牌
        FeishuUserAccessTokenRequest request = convertRequest(authorizationGrantRequest);
        // 获取客户端标识
        String clientId = authorizationGrantRequest.getClientRegistration().getClientId();
        String clientSecret = authorizationGrantRequest.getClientRegistration().getClientSecret();
        // 获取用户访问令牌响应
        FeishuUserAccessTokenResponse response = feishuHelper.getUserAccessToken(request, clientId, clientSecret);
        // 转换响应
        return convertResponse(response);
    }

    /**
     * 转换请求
     *
     * @param authorizationGrantRequest 请求
     * @return 请求
     */
    private FeishuUserAccessTokenRequest convertRequest(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        FeishuUserAccessTokenRequest request = new FeishuUserAccessTokenRequest();
        request.setCode(authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        request.setGrantType(authorizationGrantRequest.getGrantType().getValue());
        return request;
    }

    /**
     * 转换响应
     *
     * @param response 响应
     * @return 响应
     */
    private OAuth2AccessTokenResponse convertResponse(FeishuUserAccessTokenResponse response) {
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken())
                .tokenType(convertTokenType(response.getTokenType()))
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
    private Map<String, Object> convertAdditionalParameters(FeishuUserAccessTokenResponse response) {
        Map<String, Object> additionalParameters = BeanUtil.beanToMap(response);
        // 移除不需要的参数
        additionalParameters.remove("accessToken");
        additionalParameters.remove("tokenType");
        additionalParameters.remove("expiresIn");
        additionalParameters.remove("refreshToken");
        additionalParameters.remove("scope");
        return additionalParameters;
    }

    /**
     * 转换作用域
     *
     * @param scope 作用域
     * @return 作用域
     */
    private Set<String> convertScopes(String scope) {
        return StrUtil.isBlank(scope) ? Collections.emptySet() : new HashSet<>(StrUtil.split(scope, " "));
    }

    /**
     * 转换令牌类型
     *
     * @param tokenType 令牌类型
     * @return 令牌类型
     */
    private OAuth2AccessToken.TokenType convertTokenType(String tokenType) {
        if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(tokenType)) {
            return OAuth2AccessToken.TokenType.BEARER;
        }
        return null;
    }
}
