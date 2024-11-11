package com.gls.athena.security.servlet.client.support;

import cn.hutool.core.lang.TypeReference;
import com.gls.athena.starter.data.redis.support.RedisUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Component;

/**
 * Redis OAuth2 授权客户端服务
 *
 * @author george
 */
@Component
public class RedisOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    /**
     * 授权客户端缓存名称
     */
    private static final String CACHE_NAME = "oauth2:authorized:client";

    /**
     * 加载授权客户端 {@link OAuth2AuthorizedClient} 实例 (如果存在) 以用于指定的客户端和用户 {@code Principal} (Resource Owner)。
     *
     * @param clientRegistrationId 客户端注册标识
     * @param principalName        用户 {@code Principal} 名称
     * @param <T>                  授权客户端类型
     * @return 授权客户端 {@link OAuth2AuthorizedClient} 实例 (如果存在) 或 {@code null}
     */
    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return RedisUtil.getCacheValue(CACHE_NAME, clientRegistrationId + ":" + principalName, new TypeReference<>() {
        });
    }

    /**
     * 保存授权客户端 {@link OAuth2AuthorizedClient} 实例。
     *
     * @param authorizedClient 授权客户端 {@link OAuth2AuthorizedClient} 实例
     * @param principal        用户 {@code Principal} 名称
     */
    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        RedisUtil.setCacheValue(CACHE_NAME, authorizedClient.getClientRegistration().getRegistrationId() + ":" + principal.getName(), authorizedClient);
    }

    /**
     * 移除授权客户端 {@link OAuth2AuthorizedClient} 实例。
     *
     * @param clientRegistrationId 客户端注册标识
     * @param principalName        用户 {@code Principal} 名称
     */
    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        RedisUtil.deleteCacheValue(CACHE_NAME, clientRegistrationId + ":" + principalName);
    }
}
