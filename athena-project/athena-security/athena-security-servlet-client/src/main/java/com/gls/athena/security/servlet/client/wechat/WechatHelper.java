package com.gls.athena.security.servlet.client.wechat;

import com.gls.athena.security.servlet.client.wechat.domain.*;
import com.gls.athena.starter.data.redis.support.RedisUtil;
import lombok.experimental.UtilityClass;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * 小程序助手
 *
 * @author george
 */
@UtilityClass
public class WechatHelper {

    /**
     * 获取 RestTemplate
     *
     * @return RestTemplate
     */
    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 添加消息转换器
        restTemplate.getMessageConverters().add(new WechatHttpMessageConverter());
        return restTemplate;
    }

    /**
     * 获取小程序访问令牌
     *
     * @param appId     小程序 ID
     * @param appSecret 小程序密钥
     * @return 小程序访问令牌
     */
    public MiniAccessTokenResponse getMiniAccessToken(String appId, String appSecret, String appAccessTokenUri) {
        // 从缓存中获取小程序访问令牌
        MiniAccessTokenResponse response = RedisUtil.getCacheValue(WechatConstants.WECHAT_MINI_ACCESS_TOKEN_CACHE_NAME, appId, MiniAccessTokenResponse.class);
        if (response != null) {
            return response;
        }
        // 请求小程序访问令牌
        MiniAccessTokenRequest request = new MiniAccessTokenRequest();
        request.setAppid(appId);
        request.setSecret(appSecret);
        request.setGrantType("client_credential");
        // 获取小程序访问令牌
        response = getMiniAccessToken(request, appAccessTokenUri);
        // 缓存小程序访问令牌
        if (response != null) {
            RedisUtil.setCacheValue(WechatConstants.WECHAT_MINI_ACCESS_TOKEN_CACHE_NAME, appId, response, response.getExpiresIn(), TimeUnit.SECONDS);
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
    private MiniAccessTokenResponse getMiniAccessToken(MiniAccessTokenRequest request, String appAccessTokenUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(appAccessTokenUri)
                .queryParam("appid", request.getAppid())
                .queryParam("secret", request.getSecret())
                .queryParam("grant_type", request.getGrantType())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, MiniAccessTokenResponse.class).getBody();
    }

    /**
     * 小程序登录
     *
     * @param request 请求
     * @return 小程序登录
     */
    public MiniUserInfoResponse getMiniUserInfo(MiniUserInfoRequest request, String userInfoUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(userInfoUri)
                .queryParam("appid", request.getAppId())
                .queryParam("secret", request.getSecret())
                .queryParam("js_code", request.getJsCode())
                .queryParam("grant_type", request.getGrantType())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, MiniUserInfoResponse.class).getBody();
    }

    /**
     * 获取微信访问令牌
     *
     * @param request 请求
     * @return 微信访问令牌
     */
    public WechatAccessTokenResponse getWechatAccessToken(WechatAccessTokenRequest request, String accessTokenUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(accessTokenUri)
                .queryParam("appid", request.getAppid())
                .queryParam("secret", request.getSecret())
                .queryParam("code", request.getCode())
                .queryParam("grant_type", request.getGrantType())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, WechatAccessTokenResponse.class).getBody();
    }

    /**
     * 获取微信用户
     *
     * @param request 请求
     * @return 微信用户
     */
    public WechatUserInfoResponse getWechatUserInfo(WechatUserInfoRequest request, String userInfoUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(userInfoUri)
                .queryParam("access_token", request.getAccessToken())
                .queryParam("openid", request.getOpenid())
                .queryParam("lang", request.getLang())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, WechatUserInfoResponse.class).getBody();
    }

    /**
     * 获取企业微信访问令牌
     *
     * @param corpid     企业 ID
     * @param corpsecret 企业密钥
     * @return 企业微信访问令牌
     */
    public WorkAccessTokenResponse getWorkAccessToken(String corpid, String corpsecret, String accessTokenUri) {
        WorkAccessTokenResponse response = RedisUtil.getCacheValue(WechatConstants.WECHAT_WORK_ACCESS_TOKEN_CACHE_NAME, corpid, WorkAccessTokenResponse.class);
        if (response != null) {
            return response;
        }
        WorkAccessTokenRequest request = new WorkAccessTokenRequest();
        request.setCorpid(corpid);
        request.setCorpsecret(corpsecret);
        response = getWorkAccessToken(request, accessTokenUri);
        if (response != null) {
            RedisUtil.setCacheValue(WechatConstants.WECHAT_WORK_ACCESS_TOKEN_CACHE_NAME, corpid, response, response.getExpiresIn(), TimeUnit.SECONDS);
            return response;
        }
        return null;
    }

    /**
     * 获取企业微信访问令牌
     *
     * @param request 请求
     * @return 企业微信访问令牌
     */
    private WorkAccessTokenResponse getWorkAccessToken(WorkAccessTokenRequest request, String accessTokenUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(accessTokenUri)
                .queryParam("corpid", request.getCorpid())
                .queryParam("corpsecret", request.getCorpsecret())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, WorkAccessTokenResponse.class).getBody();
    }

    /**
     * 获取企业微信用户登录身份
     *
     * @param request 请求
     * @return 企业微信用户登录身份
     */
    public WorkUserLoginResponse getWorkUserLogin(WorkUserLoginRequest request, String userLoginUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(userLoginUri)
                .queryParam("access_token", request.getAccessToken())
                .queryParam("code", request.getCode())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, WorkUserLoginResponse.class).getBody();
    }

    /**
     * 获取企业微信用户信息
     *
     * @param request 请求
     * @return 企业微信用户信息
     */
    public WorkUserInfoResponse getWorkUserInfo(WorkUserInfoRequest request, String userInfoUri) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(userInfoUri)
                .queryParam("access_token", request.getAccessToken())
                .queryParam("userid", request.getUserid())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, WorkUserInfoResponse.class).getBody();
    }
}
