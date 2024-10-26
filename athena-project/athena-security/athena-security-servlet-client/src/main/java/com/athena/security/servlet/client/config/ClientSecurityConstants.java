package com.athena.security.servlet.client.config;

/**
 * 客户端安全常量
 *
 * @author george
 */
public interface ClientSecurityConstants {
    /**
     * 社交用户会话键
     */
    String SOCIAL_USER_SESSION_KEY = "SOCIAL_USER";
    /**
     * 小程序accessToken缓存名
     */
    String MINI_APP_ACCESS_TOKEN_CACHE_NAME = "mini_app:access_token";
    /**
     * 企业微信accessToken缓存名
     */
    String WECHAT_WORK_ACCESS_TOKEN_CACHE_NAME = "work_wechat:access_token";
}

