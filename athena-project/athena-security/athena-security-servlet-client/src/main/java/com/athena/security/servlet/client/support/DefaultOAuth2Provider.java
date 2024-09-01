package com.athena.security.servlet.client.support;

import cn.hutool.extra.spring.SpringUtil;
import com.athena.security.servlet.client.feishu.domian.FeishuProperties;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * 默认OAuth2提供者
 */
public enum DefaultOAuth2Provider {
    /**
     * 微信开放平台
     */
    WECHAT_OPEN {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope("snsapi_login");
            builder.authorizationUri("https://open.weixin.qq.com/connect/qrconnect");
            builder.tokenUri("https://api.weixin.qq.com/sns/oauth2/access_token");
            builder.userInfoUri("https://api.weixin.qq.com/sns/userinfo");
            builder.userNameAttributeName("unionid");
            builder.clientName("微信开放平台");
            return builder;
        }
    },
    /**
     * 微信公众平台
     */
    WECHAT_MP {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope("snsapi_userinfo");
            builder.authorizationUri("https://open.weixin.qq.com/connect/oauth2/authorize");
            builder.tokenUri("https://api.weixin.qq.com/sns/oauth2/access_token");
            builder.userInfoUri("https://api.weixin.qq.com/sns/userinfo");
            builder.userNameAttributeName("unionid");
            builder.clientName("微信公众平台");
            return builder;
        }
    },
    /**
     * 企业微信
     */
    WECHAT_WORK {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.authorizationUri("https://login.work.weixin.qq.com/wwlogin/sso/login");
            builder.tokenUri("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
            builder.userInfoUri("https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo");
            builder.userNameAttributeName("openid");
            builder.clientName("企业微信");
            return builder;
        }
    },
    /**
     * 飞书
     */
    FEISHU {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            FeishuProperties feishuProperties = SpringUtil.getBean(FeishuProperties.class);
            builder.authorizationUri(feishuProperties.getAuthorizationUri());
            builder.tokenUri(feishuProperties.getTokenUri());
            builder.userInfoUri(feishuProperties.getUserInfoUri());
            builder.userNameAttributeName(feishuProperties.getUserNameAttribute());
            builder.clientName(feishuProperties.getClientName());
            return builder;
        }
    };
    /**
     * 默认回调地址
     */
    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";

    /**
     * 获取Builder
     *
     * @param registrationId 注册ID
     * @return Builder
     */
    public abstract ClientRegistration.Builder getBuilder(String registrationId);

    /**
     * 获取Builder
     *
     * @param registrationId 注册ID
     * @param method         认证方式
     * @param redirectUri    回调地址
     * @return Builder
     */
    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method,
                                                          String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }
}