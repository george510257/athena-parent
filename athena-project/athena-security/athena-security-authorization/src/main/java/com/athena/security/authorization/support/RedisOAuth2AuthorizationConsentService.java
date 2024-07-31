package com.athena.security.authorization.support;

import com.athena.starter.data.redis.support.RedisUtil;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

public class RedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private static final String CACHE_NAME = "authorization:consent";

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        RedisUtil.setCacheValue(buildKey(authorizationConsent), authorizationConsent);
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        RedisUtil.deleteCacheValue(buildKey(authorizationConsent));
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return RedisUtil.getCacheValue(buildKey(registeredClientId, principalName), OAuth2AuthorizationConsent.class);
    }

    private String buildKey(String registeredClientId, String principalName) {
        return CACHE_NAME + RedisUtil.SEPARATOR + registeredClientId + RedisUtil.SEPARATOR + principalName;
    }

    private String buildKey(OAuth2AuthorizationConsent authorizationConsent) {
        return buildKey(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }
}
