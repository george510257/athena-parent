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

@Component
public class FeishuHelper {
    @Resource
    private FeishuProperties feishuProperties;

    public String getAppAccessToken(String clientId, String clientSecret) {
        AppAccessTokenResponse response = RedisUtil.getCacheValue(feishuProperties.getCacheName(), clientId, AppAccessTokenResponse.class);
        if (response != null) {
            return response.getAppAccessToken();
        }
        AppAccessTokenRequest request = new AppAccessTokenRequest();
        request.setAppId(clientId);
        request.setAppSecret(clientSecret);
        response = getAppAccessToken(request);
        if (response != null) {
            RedisUtil.setCacheValue(feishuProperties.getCacheName(), clientId, response, response.getExpire(), TimeUnit.MILLISECONDS);
            return response.getAppAccessToken();
        }
        return "";
    }

    private AppAccessTokenResponse getAppAccessToken(AppAccessTokenRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<AppAccessTokenRequest> requestEntity = RequestEntity
                .post(URI.create("https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(request);
        return restTemplate.exchange(requestEntity, AppAccessTokenResponse.class).getBody();
    }

}
