package com.athena.security.servlet.client.feishu;

import com.athena.security.servlet.client.feishu.domian.AppAccessTokenRequest;
import com.athena.security.servlet.client.feishu.domian.AppAccessTokenResponse;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import com.athena.starter.data.redis.support.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * 飞书助手
 */
@Component
public class FeishuHelper {
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
        AppAccessTokenResponse response = RedisUtil.getCacheValue(feishuProperties.getCacheName(), clientId, AppAccessTokenResponse.class);
        if (response != null) {
            return response.getAppAccessToken();
        }
        // 请求应用访问令牌
        AppAccessTokenRequest request = new AppAccessTokenRequest();
        request.setAppId(clientId);
        request.setAppSecret(clientSecret);
        // 获取应用访问令牌
        response = getAppAccessToken(request);
        // 缓存应用访问令牌
        if (response != null) {
            RedisUtil.setCacheValue(feishuProperties.getCacheName(), clientId, response, response.getExpire(), TimeUnit.SECONDS);
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
    private AppAccessTokenResponse getAppAccessToken(AppAccessTokenRequest request) {
        // 请求飞书接口
        RestTemplate restTemplate = new RestTemplate();
        // 请求实体
        RequestEntity<AppAccessTokenRequest> requestEntity = RequestEntity
                .post(URI.create("https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(request);
        // 返回应用访问令牌
        return restTemplate.exchange(requestEntity, AppAccessTokenResponse.class).getBody();
    }

}
