package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.feishu.domian.*;
import com.athena.starter.data.redis.support.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * 飞书助手
 *
 * @author george
 */
@Component
public class FeishuHelper {
    /**
     * 应用访问令牌缓存名称
     */
    private static final String APP_ACCESS_TOKEN_CACHE_NAME = "feishu:app_access_token";

    /**
     * 飞书属性配置
     */
    @Resource
    private FeishuProperties feishuProperties;

    /**
     * 获取应用访问令牌
     *
     * @param clientId     应用 ID
     * @param clientSecret 应用密钥
     * @return 应用访问令牌
     */
    public String getAppAccessToken(String clientId, String clientSecret) {
        // 从缓存中获取应用访问令牌
        FeishuAppAccessTokenResponse response = RedisUtil.getCacheValue(APP_ACCESS_TOKEN_CACHE_NAME, clientId, FeishuAppAccessTokenResponse.class);
        if (response != null) {
            return response.getAppAccessToken();
        }
        // 请求应用访问令牌
        FeishuAppAccessTokenRequest request = new FeishuAppAccessTokenRequest();
        request.setAppId(clientId);
        request.setAppSecret(clientSecret);
        // 获取应用访问令牌
        response = getAppAccessToken(request);
        // 缓存应用访问令牌
        if (response != null) {
            RedisUtil.setCacheValue(APP_ACCESS_TOKEN_CACHE_NAME, clientId, response, response.getExpire(), TimeUnit.SECONDS);
            return response.getAppAccessToken();
        }
        // 返回空
        return null;
    }

    /**
     * 获取应用访问令牌
     *
     * @param request 应用访问令牌请求
     * @return 应用访问令牌
     */
    private FeishuAppAccessTokenResponse getAppAccessToken(FeishuAppAccessTokenRequest request) {
        // 请求飞书接口
        RestTemplate restTemplate = new RestTemplate();
        // 请求实体
        RequestEntity<FeishuAppAccessTokenRequest> requestEntity = RequestEntity
                .post(URI.create(feishuProperties.getAppAccessTokenUri()))
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(request);
        // 返回应用访问令牌
        return restTemplate.exchange(requestEntity, FeishuAppAccessTokenResponse.class).getBody();
    }

    /**
     * 获取用户访问令牌
     *
     * @param request 用户访问令牌请求
     * @return 用户访问令牌
     */
    public FeishuUserAccessTokenResponse getUserAccessToken(FeishuUserAccessTokenRequest request, String clientId, String clientSecret) {
        // 请求飞书接口
        RestTemplate restTemplate = new RestTemplate();
        // 请求实体
        RequestEntity<FeishuUserAccessTokenRequest> requestEntity = RequestEntity
                .post(URI.create(feishuProperties.getTokenUri()))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + getAppAccessToken(clientId, clientSecret))
                .body(request);
        // 返回用户访问令牌
        FeishuResponse<FeishuUserAccessTokenResponse> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<FeishuResponse<FeishuUserAccessTokenResponse>>() {
        }).getBody();
        if (response != null) {
            return response.getData();
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param userAccessToken 用户访问令牌
     * @return 用户信息
     */
    public FeishuUserInfoResponse getUserInfo(String userAccessToken) {
        // 请求飞书接口
        RestTemplate restTemplate = new RestTemplate();
        // 请求实体
        RequestEntity<?> requestEntity = RequestEntity
                .get(URI.create(feishuProperties.getUserInfoUri()))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + userAccessToken)
                .build();
        // 返回用户信息
        FeishuResponse<FeishuUserInfoResponse> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<FeishuResponse<FeishuUserInfoResponse>>() {
        }).getBody();
        if (response != null) {
            return response.getData();
        }
        return null;
    }
}
