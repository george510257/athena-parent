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
     * 用户名
     */
    private final String username;
    /**
     * 密码
     */
    private final String password;
    /**
     * 作用域
     */
    private final Set<String> scopes;

    /**
     * 构造函数
     *
     * @param username             用户名
     * @param password             密码
     * @param scopes               作用域
     * @param clientPrincipal      客户端主体
     * @param additionalParameters 附加参数
     */
    protected OAuth2PasswordAuthenticationToken(String username, String password, Set<String> scopes, Authentication clientPrincipal, Map<String, Object> additionalParameters) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        this.username = username;
        this.password = password;
        this.scopes = scopes;
    }
}
