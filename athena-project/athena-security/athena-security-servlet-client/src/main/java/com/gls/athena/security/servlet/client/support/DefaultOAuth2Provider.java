package com.gls.athena.security.servlet.client.support;

import cn.hutool.extra.spring.SpringUtil;
import com.gls.athena.security.servlet.client.config.ClientSecurityConstants;
import com.gls.athena.security.servlet.client.config.ClientSecurityProperties;
import com.gls.athena.security.servlet.client.feishu.FeishuConstants;
import com.gls.athena.security.servlet.client.wechat.WechatConstants;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            ClientSecurityProperties properties = SpringUtil.getBean(ClientSecurityProperties.class);
            ClientSecurityProperties.WechatOpen wechatOpen = properties.getWechatOpen()
                    .getOrDefault(registrationId, new ClientSecurityProperties.WechatOpen());
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope(Set.of("snsapi_login"));
            builder.authorizationUri("https://open.weixin.qq.com/connect/qrconnect");
            builder.tokenUri("https://api.weixin.qq.com/sns/oauth2/access_token");
            builder.userInfoUri("https://api.weixin.qq.com/sns/userinfo");
            builder.userNameAttributeName("openid");
            builder.clientName("微信开放平台");
            Map<String, Object> metadata = new HashMap<>();
            metadata.put(ClientSecurityConstants.PROVIDER_ID, WechatConstants.WECHAT_OPEN_PROVIDER_ID);
            metadata.put("lang", wechatOpen.getLang());
            builder.providerConfigurationMetadata(metadata);
            return builder;
        }
    },
    /**
     * 微信公众平台
     */
    WECHAT_MP {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientSecurityProperties properties = SpringUtil.getBean(ClientSecurityProperties.class);
            ClientSecurityProperties.WechatMp wechatMp = properties.getWechatMp()
                    .getOrDefault(registrationId, new ClientSecurityProperties.WechatMp());
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope(Set.of("snsapi_userinfo"));
            builder.authorizationUri("https://open.weixin.qq.com/connect/oauth2/authorize");
            builder.tokenUri("https://api.weixin.qq.com/sns/oauth2/access_token");
            builder.userInfoUri("https://api.weixin.qq.com/sns/userinfo");
            builder.userNameAttributeName("openid");
            builder.clientName("微信公众号");
            Map<String, Object> metadata = new HashMap<>();
            metadata.put(ClientSecurityConstants.PROVIDER_ID, WechatConstants.WECHAT_MP_PROVIDER_ID);
            metadata.put("lang", wechatMp.getLang());
            builder.providerConfigurationMetadata(metadata);
            return builder;
        }
    },
    /**
     * 微信小程序
     */
    WECHAT_MINI {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope(Set.of("snsapi_userinfo"));
            builder.authorizationUri("/login/oauth2/code/wechat_mini");
            builder.tokenUri("https://api.weixin.qq.com/cgi-bin/token");
            builder.userInfoUri("https://api.weixin.qq.com/sns/jscode2session");
            builder.userNameAttributeName("openid");
            builder.clientName("微信小程序");
            Map<String, Object> metadata = new HashMap<>();
            metadata.put(ClientSecurityConstants.PROVIDER_ID, WechatConstants.WECHAT_MINI_PROVIDER_ID);
            builder.providerConfigurationMetadata(metadata);
            return builder;
        }
    },
    /**
     * 企业微信
     */
    WECHAT_WORK {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientSecurityProperties properties = SpringUtil.getBean(ClientSecurityProperties.class);
            ClientSecurityProperties.WechatWork wechatWork = properties.getWechatWork()
                    .getOrDefault(registrationId, new ClientSecurityProperties.WechatWork());
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_REDIRECT_URL);
            builder.scope(Set.of("snsapi_base"));
            builder.authorizationUri("https://login.work.weixin.qq.com/wwlogin/sso/login");
            builder.tokenUri("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
            builder.userInfoUri("https://qyapi.weixin.qq.com/cgi-bin/user/get");
            builder.userNameAttributeName("id");
            builder.clientName("企业微信");
            Map<String, Object> metadata = new HashMap<>();
            metadata.put(ClientSecurityConstants.PROVIDER_ID, WechatConstants.WECHAT_WORK_PROVIDER_ID);
            metadata.put(WechatConstants.WECHAT_WORK_USER_LOGIN_URI_NAME, "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo");
            metadata.put("loginType", wechatWork.getLoginType().getValue());
            metadata.put("agentId", wechatWork.getAgentId());
            metadata.put("lang", wechatWork.getLang());
            builder.providerConfigurationMetadata(metadata);
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
            builder.authorizationUri("https://open.feishu.cn/open-apis/authen/v1/authorize");
            builder.tokenUri("https://open.feishu.cn/open-apis/authen/v1/oidc/access_token");
            builder.userInfoUri("https://open.feishu.cn/open-apis/authen/v1/user_info");
            builder.userNameAttributeName("unionId");
            builder.clientName("飞书");
            Map<String, Object> metadata = new HashMap<>();
            metadata.put(FeishuConstants.APP_ACCESS_TOKEN_URL_NAME, "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal");
            metadata.put(ClientSecurityConstants.PROVIDER_ID, FeishuConstants.PROVIDER_ID);
            builder.providerConfigurationMetadata(metadata);
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