package com.athena.security.servlet.client.support;

import cn.hutool.extra.spring.SpringUtil;
import com.athena.security.servlet.client.feishu.FeishuProperties;
import com.athena.security.servlet.client.wechat.WechatProperties;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * 默认OAuth2提供者
 *
 * @author george
 */
public enum DefaultOAuth2Provider {
    /**
     * 微信开放平台
     */
    WECHAT_OPEN {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            WechatProperties.Open open = SpringUtil.getBean(WechatProperties.class).getOpen();
            builder.scope(open.getScopes());
            builder.authorizationUri(open.getAuthorizationUri());
            builder.tokenUri(open.getTokenUri());
            builder.userInfoUri(open.getUserInfoUri());
            builder.userNameAttributeName(open.getUserNameAttribute());
            builder.clientName(open.getClientName());
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
            WechatProperties.Mp mp = SpringUtil.getBean(WechatProperties.class).getMp();
            builder.scope(mp.getScopes());
            builder.authorizationUri(mp.getAuthorizationUri());
            builder.tokenUri(mp.getTokenUri());
            builder.userInfoUri(mp.getUserInfoUri());
            builder.userNameAttributeName(mp.getUserNameAttribute());
            builder.clientName(mp.getClientName());
            return builder;
        }
    },
    /**
     * 微信小程序
     */
    WECHAT_MINI_APP {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            WechatProperties.MiniApp miniApp = SpringUtil.getBean(WechatProperties.class).getMiniApp();
            builder.scope(miniApp.getScopes());
            builder.authorizationUri(miniApp.getAuthorizationUri());
            builder.tokenUri(miniApp.getTokenUri());
            builder.userInfoUri(miniApp.getUserInfoUri());
            builder.userNameAttributeName(miniApp.getUserNameAttribute());
            builder.clientName(miniApp.getClientName());
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
            WechatProperties.Work work = SpringUtil.getBean(WechatProperties.class).getWork();
            builder.scope(work.getScopes());
            builder.authorizationUri(work.getAuthorizationUri());
            builder.tokenUri(work.getTokenUri());
            builder.userInfoUri(work.getUserInfoUri());
            builder.userNameAttributeName(work.getUserNameAttribute());
            builder.clientName(work.getClientName());
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