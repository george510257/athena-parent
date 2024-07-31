package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationModule extends SimpleModule {

    public AuthorizationModule() {
        super(AuthorizationModule.class.getName(), new Version(1, 0, 0, null, "com.athena.security", "athena-security-authorization"));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(AuthorizationGrantType.class, AuthorizationGrantTypeMixin.class);
        context.setMixInAnnotations(OAuth2AccessToken.class, OAuth2AccessTokenMixin.class);
        context.setMixInAnnotations(OAuth2AccessToken.TokenType.class, OAuth2AccessTokenTypeMixin.class);
        context.setMixInAnnotations(OAuth2AuthorizationCode.class, OAuth2AuthorizationCodeMixin.class);
        context.setMixInAnnotations(OAuth2AuthorizationConsent.class, OAuth2AuthorizationConsentMixin.class);
        context.setMixInAnnotations(OAuth2Authorization.class, OAuth2AuthorizationMixin.class);
        context.setMixInAnnotations(OAuth2Authorization.Token.class, OAuth2AuthorizationTokenMixin.class);
        context.setMixInAnnotations(OAuth2DeviceCode.class, OAuth2DeviceCodeMixin.class);
        context.setMixInAnnotations(OAuth2RefreshToken.class, OAuth2RefreshTokenMixin.class);
        context.setMixInAnnotations(OAuth2UserCode.class, OAuth2UserCodeMixin.class);
        context.setMixInAnnotations(OidcIdToken.class, OidcIdTokenMixin.class);
    }
}
