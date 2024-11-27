package com.gls.athena.security.servlet.client.wechat;

/**
 * 微信常量
 *
 * @author george
 */
public interface WechatConstants {
    /**
     * 微信开放平台ID
     */
    String WECHAT_OPEN_PROVIDER_ID = "wechat_open";
    /**
     * 微信公众平台ID
     */
    String WECHAT_MP_PROVIDER_ID = "wechat_mp";
    /**
     * 微信小程序ID
     */
    String WECHAT_MINI_PROVIDER_ID = "wechat_mini";
    /**
     * 微信小程序accessToken缓存名
     */
    String WECHAT_MINI_ACCESS_TOKEN_CACHE_NAME = "wechat_mini:access_token";
    /**
     * 企业微信ID
     */
    String WECHAT_WORK_PROVIDER_ID = "wechat_work";
    /**
     * 企业微信accessToken缓存名
     */
    String WECHAT_WORK_ACCESS_TOKEN_CACHE_NAME = "wechat_work:access_token";
    /**
     * 企业微信appAccessToken URI 名称
     */
    String WECHAT_WORK_USER_LOGIN_URI_NAME = "workUserLoginUri";
}
