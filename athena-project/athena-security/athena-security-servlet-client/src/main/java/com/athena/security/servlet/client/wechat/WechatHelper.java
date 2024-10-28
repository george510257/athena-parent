package com.athena.security.servlet.client.wechat;

import com.athena.security.servlet.client.config.ClientSecurityConstants;
import com.athena.security.servlet.client.wechat.domain.*;
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
public class WechatHelper {

    /**
     * 微信配置属性
     */
    @Resource
    private WechatProperties wechatProperties;

    /**
     * @return
     */
    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //
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
    public MiniAppAccessTokenResponse getMiniAppAccessToken(String appId, String appSecret) {
        // 从缓存中获取小程序访问令牌
        MiniAppAccessTokenResponse response = RedisUtil.getCacheValue(ClientSecurityConstants.MINI_APP_ACCESS_TOKEN_CACHE_NAME, appId, MiniAppAccessTokenResponse.class);
        if (response != null) {
            return response;
        }
        // 请求小程序访问令牌
        MiniAppAccessTokenRequest request = new MiniAppAccessTokenRequest();
        request.setAppid(appId);
        request.setSecret(appSecret);
        request.setGrantType("client_credential");
        // 获取小程序访问令牌
        response = getMiniAppAccessToken(request);
        // 缓存小程序访问令牌
        if (response != null) {
            RedisUtil.setCacheValue(ClientSecurityConstants.MINI_APP_ACCESS_TOKEN_CACHE_NAME, appId, response, response.getExpiresIn(), TimeUnit.SECONDS);
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
    private MiniAppAccessTokenResponse getMiniAppAccessToken(MiniAppAccessTokenRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getMiniApp().getAppAccessTokenUri())
                .queryParam("appid", request.getAppid())
                .queryParam("secret", request.getSecret())
                .queryParam("grant_type", request.getGrantType())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, MiniAppAccessTokenResponse.class).getBody();
    }

    /**
     * 小程序登录
     *
     * @param request 请求
     * @return 小程序登录
     */
    public MiniAppUserInfoResponse getMiniAppUserInfo(MiniAppUserInfoRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getMiniApp().getTokenUri())
                .queryParam("appid", request.getAppId())
                .queryParam("secret", request.getSecret())
                .queryParam("js_code", request.getJsCode())
                .queryParam("grant_type", request.getGrantType())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, MiniAppUserInfoResponse.class).getBody();
    }

    /**
     * 获取微信访问令牌
     *
     * @param request 请求
     * @return 微信访问令牌
     */
    public WechatAccessTokenResponse getWechatAccessToken(WechatAccessTokenRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getOpen().getTokenUri())
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
    public WechatUserInfoResponse getWechatUserInfo(WechatUserInfoRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getOpen().getUserInfoUri())
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
    public WorkAccessTokenResponse getWorkAccessToken(String corpid, String corpsecret) {
        WorkAccessTokenResponse response = RedisUtil.getCacheValue(ClientSecurityConstants.WECHAT_WORK_ACCESS_TOKEN_CACHE_NAME, corpid, WorkAccessTokenResponse.class);
        if (response != null) {
            return response;
        }
        WorkAccessTokenRequest request = new WorkAccessTokenRequest();
        request.setCorpid(corpid);
        request.setCorpsecret(corpsecret);
        response = getWorkAccessToken(request);
        if (response != null) {
            RedisUtil.setCacheValue(ClientSecurityConstants.WECHAT_WORK_ACCESS_TOKEN_CACHE_NAME, corpid, response, response.getExpiresIn(), TimeUnit.SECONDS);
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
    private WorkAccessTokenResponse getWorkAccessToken(WorkAccessTokenRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getWork().getTokenUri())
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
    public WorkUserLoginResponse getWorkUserLogin(WorkUserLoginRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getWork().getUserLoginUri())
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
    public WorkUserInfoResponse getWorkUserInfo(WorkUserInfoRequest request) {
        RestTemplate restTemplate = getRestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(wechatProperties.getWork().getUserInfoUri())
                .queryParam("access_token", request.getAccessToken())
                .queryParam("userid", request.getUserid())
                .build().toUri();
        RequestEntity<?> requestEntity = RequestEntity.get(uri).build();
        return restTemplate.exchange(requestEntity, WorkUserInfoResponse.class).getBody();
    }
}
