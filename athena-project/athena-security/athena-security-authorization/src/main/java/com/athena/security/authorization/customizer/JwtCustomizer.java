package com.athena.security.authorization.customizer;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

/**
 * jwt自定义
 */
@Component
public class JwtCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    /**
     * 自定义jwt
     *
     * @param context jwt上下文
     */
    @Override
    public void customize(JwtEncodingContext context) {
        // 客户端模式不设置用户信息
        if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType())) {
            return;
        }
        // 设置用户信息
        Object principal = context.getPrincipal().getPrincipal();
        context.getClaims().claim("principal", principal);
    }
}
