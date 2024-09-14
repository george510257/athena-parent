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
     * 微信应用 id
     */
    String WECHAT_APP_ID = "appId";
    /**
     * 微信应用密钥
     */
    String WECHAT_APP_SECRET = "secret";
    /**
     * 微信重定向
     */
    String WECHAT_REDIRECT = "wechat_redirect";
    /**
     * 微信openid
     */
    String WECHAT_OPENID = "openid";
    /**
     * 微信语言
     */
    String WECHAT_LANG = "lang";
    /**
     * 企业 id
     */
    String WECHAT_WORK_CORP_ID = "corpid";
    /**
     * 企业密钥
     */
    String WECHAT_WORK_CORP_SECRET = "corpsecret";
    /**
     * 应用 id
     */
    String WECHAT_WORK_AGENT_ID = "agentid";
    /**
     * 企业微信登录类型
     */
    String WECHAT_WORK_LOGIN_TYPE = "login_type";
    /**
     * 小程序 code
     */
    String MINI_APP_CODE = "js_code";
}

