package com.athena.security.authorization.support;

import com.athena.starter.data.redis.support.RedisUtil;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.util.List;

/**
 * Redis OAuth2授权服务
 */
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    /**
     * 缓存名称
     */
    private static final String CACHE_NAME = "authorization";

    /**
     * 保存授权
     *
     * @param authorization the {@link OAuth2Authorization}
     */
    @Override
    public void save(OAuth2Authorization authorization) {
        RedisUtil.setCacheValue(CACHE_NAME, authorization.getId(), authorization);
    }

    /**
     * 删除授权
     *
     * @param authorization the {@link OAuth2Authorization}
     */
    @Override
    public void remove(OAuth2Authorization authorization) {
        RedisUtil.deleteCacheValue(CACHE_NAME, authorization.getId());
    }

    /**
     * 根据id查找授权
     *
     * @param id the authorization identifier
     * @return the {@link OAuth2Authorization} or {@code null} if not found
     */
    @Override
    public OAuth2Authorization findById(String id) {
        return RedisUtil.getCacheValue(CACHE_NAME, id, OAuth2Authorization.class);
    }

    /**
     * 根据token查找授权
     *
     * @param token     令牌
     * @param tokenType 令牌类型
     * @return 授权
     */
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        List<OAuth2Authorization> authorizations = RedisUtil.getCacheValueList(CACHE_NAME, OAuth2Authorization.class);
        return authorizations.stream()
                .filter(authorization -> hasToken(authorization, token, tokenType))
                .findFirst()
                .orElse(null);
    }

    /**
     * 是否有token
     *
     * @param authorization 授权
     * @param token         令牌
     * @param tokenType     令牌类型
     * @return 是否有token
     */
    private boolean hasToken(OAuth2Authorization authorization, String token, OAuth2TokenType tokenType) {
        if (tokenType == null) {
            return matchesState(authorization, token)
                    || matchesCode(authorization, token)
                    || matchesAccessToken(authorization, token)
                    || matchesRefreshToken(authorization, token)
                    || matchesIdToken(authorization, token)
                    || matchesDeviceCode(authorization, token)
                    || matchesUserCode(authorization, token);
        }
        if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return matchesState(authorization, token);
        }
        if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return matchesCode(authorization, token);
        }
        if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            return matchesAccessToken(authorization, token);
        }
        if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            return matchesRefreshToken(authorization, token);
        }
        if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            return matchesIdToken(authorization, token);
        }
        if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            return matchesDeviceCode(authorization, token);
        }
        if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            return matchesUserCode(authorization, token);
        }
        return false;
    }

    /**
     * 匹配用户代码
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesUserCode(OAuth2Authorization authorization, String token) {
        OAuth2Authorization.Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);
        return userCode != null && token.equals(userCode.getToken().getTokenValue());
    }

    /**
     * 匹配设备代码
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesDeviceCode(OAuth2Authorization authorization, String token) {
        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);
        return deviceCode != null && token.equals(deviceCode.getToken().getTokenValue());
    }

    /**
     * 匹配id令牌
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesIdToken(OAuth2Authorization authorization, String token) {
        OAuth2Authorization.Token<OidcIdToken> idToken = authorization.getToken(OidcIdToken.class);
        return idToken != null && token.equals(idToken.getToken().getTokenValue());
    }

    /**
     * 匹配刷新令牌
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesRefreshToken(OAuth2Authorization authorization, String token) {
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        return refreshToken != null && token.equals(refreshToken.getToken().getTokenValue());
    }

    /**
     * 匹配访问令牌
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesAccessToken(OAuth2Authorization authorization, String token) {
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        return accessToken != null && token.equals(accessToken.getToken().getTokenValue());
    }

    /**
     * 匹配代码
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesCode(OAuth2Authorization authorization, String token) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        return authorizationCode != null && token.equals(authorizationCode.getToken().getTokenValue());
    }

    /**
     * 匹配状态
     *
     * @param authorization 授权
     * @param token         令牌
     * @return 是否匹配
     */
    private boolean matchesState(OAuth2Authorization authorization, String token) {
        return token.equals(authorization.getAttribute(OAuth2ParameterNames.STATE));
    }
}
