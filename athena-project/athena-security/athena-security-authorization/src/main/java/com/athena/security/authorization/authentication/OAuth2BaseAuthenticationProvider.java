package com.athena.security.authorization.authentication;

import com.athena.security.authorization.config.AuthorizationConstants;
import com.athena.security.authorization.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * OAuth2 基础认证提供者
 */
@Slf4j
@RequiredArgsConstructor
public abstract class OAuth2BaseAuthenticationProvider implements AuthenticationProvider {

    /**
     * 授权服务
     */
    private final OAuth2AuthorizationService authorizationService;
    /**
     * 令牌生成器
     */
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * 授权
     *
     * @param authentication 认证
     * @return 认证
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取密码认证令牌
        OAuth2BaseAuthenticationToken baseAuthenticationToken = (OAuth2BaseAuthenticationToken) authentication;
        // 获取客户端主体
        OAuth2ClientAuthenticationToken clientPrincipal = AuthenticationUtil.getAuthenticatedClientElseThrowInvalidClient(authentication);
        // 获取注册客户端
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (log.isTraceEnabled()) {
            log.trace("Retrieved registered client");
        }

        // 判断客户端是否支持密码授权类型
        if (registeredClient == null || !registeredClient.getAuthorizationGrantTypes().contains(baseAuthenticationToken.getGrantType())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE);
        }
        // 执行密码认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(baseAuthenticationToken);

        // 获取请求的范围
        Set<String> scopes = baseAuthenticationToken.getScopes();
        // 获取客户端范围
        Set<String> authorizedScopes = registeredClient.getScopes();
        // 判断请求的范围是否在客户端范围内
        if (!authorizedScopes.containsAll(scopes)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
        }

        if (log.isTraceEnabled()) {
            log.trace("Validated token request parameters");
        }

        // @formatter:off
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthenticationToken)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(baseAuthenticationToken.getGrantType())
                .authorizationGrant(baseAuthenticationToken);
        // @formatter:on

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(usernamePasswordAuthenticationToken.getName())
                .authorizationGrantType(baseAuthenticationToken.getGrantType())
                .authorizedScopes(authorizedScopes)
                .attribute(Principal.class.getName(), usernamePasswordAuthenticationToken);

        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            AuthenticationUtil.throwError(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the access token.", AuthorizationConstants.ERROR_URI);
        }

        if (log.isTraceEnabled()) {
            log.trace("Generated access token");
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (generatedRefreshToken != null) {
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    AuthenticationUtil.throwError(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate a valid refresh token.", AuthorizationConstants.ERROR_URI);
                }

                if (log.isTraceEnabled()) {
                    log.trace("Generated refresh token");
                }

                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }
        }

        // ----- ID token -----
        OidcIdToken idToken;
        if (scopes.contains(OidcScopes.OPENID)) {
            // @formatter:off
            tokenContext = tokenContextBuilder
                    .tokenType(AuthorizationConstants.ID_TOKEN_TOKEN_TYPE)
                    .authorization(authorizationBuilder.build())	// ID token customizer may need access to the access token and/or refresh token
                    .build();
            // @formatter:on
            OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof Jwt)) {
                AuthenticationUtil.throwError(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the ID token.", AuthorizationConstants.ERROR_URI);
            }

            if (log.isTraceEnabled()) {
                log.trace("Generated id token");
            }

            idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                    generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
            authorizationBuilder.token(idToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
        } else {
            idToken = null;
        }
        OAuth2Authorization authorization = authorizationBuilder.build();

        this.authorizationService.save(authorization);

        if (log.isTraceEnabled()) {
            log.trace("Saved authorization");
        }

        Map<String, Object> additionalParameters = Collections.emptyMap();
        if (idToken != null) {
            additionalParameters = new HashMap<>();
            additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
        }

        if (log.isTraceEnabled()) {
            log.trace("Authenticated token request");
        }

        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    /**
     * 获取用户名密码认证令牌
     *
     * @param baseAuthenticationToken 基础认证令牌
     * @return 用户名密码认证令牌
     */
    public abstract UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(OAuth2BaseAuthenticationToken baseAuthenticationToken);


}
