package com.athena.security.authorization.authentication;

import com.athena.security.authorization.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * OAuth2 密码认证提供者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2PasswordAuthenticationProvider implements AuthenticationProvider {

    private final AbstractUserDetailsAuthenticationProvider userDetailsAuthenticationProvider;

    /**
     * 认证
     *
     * @param authentication 认证
     * @return 认证
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 获取密码认证令牌
        OAuth2PasswordAuthenticationToken token = (OAuth2PasswordAuthenticationToken) authentication;
        // 获取客户端主体
        OAuth2ClientAuthenticationToken clientPrincipal = AuthUtil.getAuthenticatedClientElseThrowInvalidClient(authentication);
        // 获取注册客户端
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (log.isTraceEnabled()) {
            log.trace("Retrieved registered client: {}", registeredClient);
        }

        // 判断客户端是否支持密码授权类型
        if (registeredClient == null || !registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE);
        }
        // 执行密码认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(token);

        // 获取请求的范围
        Set<String> scopes = token.getScopes();
        // 获取客户端范围
        Set<String> clientScopes = registeredClient.getScopes();
        // 判断请求的范围是否在客户端范围内
        if (!clientScopes.containsAll(scopes)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
        }

        DefaultOAuth2TokenContext.Builder builder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthenticationToken)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(clientScopes)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrant(token);


        return null;
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(OAuth2PasswordAuthenticationToken token) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(token.getUsername(), token.getPassword());
        return (UsernamePasswordAuthenticationToken) userDetailsAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
    }

    /**
     * 是否支持
     *
     * @param authentication 认证
     * @return 是否支持
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
