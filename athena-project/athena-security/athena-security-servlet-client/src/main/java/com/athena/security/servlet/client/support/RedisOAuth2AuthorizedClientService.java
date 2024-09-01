package com.athena.security.servlet.client.support;

import cn.hutool.core.lang.TypeReference;
import com.athena.starter.data.redis.support.RedisUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Component;

@Component
public class RedisOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private static final String CACHE_NAME = "oauth2:authorized:client";

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return RedisUtil.getCacheValue(CACHE_NAME, clientRegistrationId + ":" + principalName, new TypeReference<>() {
        });
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        RedisUtil.setCacheValue(CACHE_NAME, authorizedClient.getClientRegistration().getRegistrationId() + ":" + principal.getName(), authorizedClient);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        RedisUtil.deleteCacheValue(CACHE_NAME, clientRegistrationId + ":" + principalName);
    }
}
