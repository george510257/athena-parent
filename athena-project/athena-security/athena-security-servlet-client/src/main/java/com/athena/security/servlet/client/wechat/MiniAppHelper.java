package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.wechat.domain.MiniAppAccessTokenRequest;
import com.athena.security.servlet.client.wechat.domain.MiniAppAccessTokenResponse;
import com.athena.starter.data.redis.support.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * 小程序助手
 *
 * @author george
 */
@Component
public class MiniAppHelper {

    /**
     * 小程序访问令牌缓存名称
     */
    private static final String APP_ACCESS_TOKEN_CACHE_NAME = "mini_app:access_token";
    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;

    /**
     * 获取小程序访问令牌
     *
     * @param appId     小程序 ID
     * @param appSecret 小程序密钥
     * @return 小程序访问令牌
     */
    public MiniAppAccessTokenResponse getAppAccessToken(String appId, String appSecret) {
        // 从缓存中获取小程序访问令牌
        MiniAppAccessTokenResponse response = RedisUtil.getCacheValue(APP_ACCESS_TOKEN_CACHE_NAME, appId, MiniAppAccessTokenResponse.class);
        if (response != null) {
            return response;
        }
        // 请求小程序访问令牌
        MiniAppAccessTokenRequest request = new MiniAppAccessTokenRequest();
        request.setAppid(appId);
        request.setSecret(appSecret);
        request.setGrantType("client_credential");
        // 获取小程序访问令牌
        response = getAppAccessToken(request);
        // 缓存小程序访问令牌
        if (response != null) {
            RedisUtil.setCacheValue(APP_ACCESS_TOKEN_CACHE_NAME, appId, response, response.getExpiresIn(), TimeUnit.SECONDS);
            return response;
        }
        // 返回空
        return null;
    }

    /**
     * 获取小程序访问令牌
     *
     * @param request 请求
     * @return 小程序访问令牌
     */
    private MiniAppAccessTokenResponse getAppAccessToken(MiniAppAccessTokenRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getMiniApp().getAppAccessTokenUri())
                .queryParam("appid", request.getAppid())
                .queryParam("secret", request.getSecret())
                .queryParam("grant_type", request.getGrantType())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, MiniAppAccessTokenResponse.class).getBody();
    }
}
