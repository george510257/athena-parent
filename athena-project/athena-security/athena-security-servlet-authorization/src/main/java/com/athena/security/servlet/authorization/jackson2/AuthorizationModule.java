package com.athena.security.servlet.authorization.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.stereotype.Component;

/**
 * 授权jackson模块
 *
 * @author george
 */
@Component
public class AuthorizationModule extends SimpleModule {

    /**
     * 构造方法
     */
    public AuthorizationModule() {
        super(AuthorizationModule.class.getName(), new Version(1, 0, 0, null, "com.athena.security", "athena-security-authorization"));
    }

    /**
     * 设置模块
     *
     * @param context 上下文
     */
    @Override
    public void setupModule(SetupContext context) {
        // 授权类型
        context.setMixInAnnotations(AuthorizationGrantType.class, AuthorizationGrantTypeMixin.class);
        // 授权令牌
        context.setMixInAnnotations(OAuth2AccessToken.class, OAuth2AccessTokenMixin.class);
        // 令牌类型
        context.setMixInAnnotations(OAuth2AccessToken.TokenType.class, OAuth2AccessTokenTypeMixin.class);
        // 授权码
        context.setMixInAnnotations(OAuth2AuthorizationCode.class, OAuth2AuthorizationCodeMixin.class);
        // 授权同意
        context.setMixInAnnotations(OAuth2AuthorizationConsent.class, OAuth2AuthorizationConsentMixin.class);
        // 授权
        context.setMixInAnnotations(OAuth2Authorization.class, OAuth2AuthorizationMixin.class);
        // 令牌
        context.setMixInAnnotations(OAuth2Authorization.Token.class, OAuth2AuthorizationTokenMixin.class);
        // 设备授权码
        context.setMixInAnnotations(OAuth2DeviceCode.class, OAuth2DeviceCodeMixin.class);
        // 刷新令牌
        context.setMixInAnnotations(OAuth2RefreshToken.class, OAuth2RefreshTokenMixin.class);
        // 用户代码
        context.setMixInAnnotations(OAuth2UserCode.class, OAuth2UserCodeMixin.class);
        // OpenID Connect Id令牌
        context.setMixInAnnotations(OidcIdToken.class, OidcIdTokenMixin.class);
    }
}
