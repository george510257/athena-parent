package com.athena.security.authorization.authentication;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

/**
 * OAuth2 密码认证令牌
 */
@Getter
public class OAuth2PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    /**
     * 作用域
     */
    private final Set<String> scopes;

    /**
     * 构造函数
     *
     * @param scopes               作用域
     * @param clientPrincipal      客户端主体
     * @param additionalParameters 附加参数
     */
    protected OAuth2PasswordAuthenticationToken(Set<String> scopes, Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = scopes;
    }
}
