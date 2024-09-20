package com.athena.security.servlet.authorization.support;

import com.athena.security.servlet.authorization.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.converter.ClaimConversionService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenIntrospection;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenIntrospectionAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认OAuth2令牌验证认证提供者
 *
 * @author george
 */
@RequiredArgsConstructor
public class DefaultOAuth2TokenIntrospectionAuthenticationProvider implements AuthenticationProvider {

    private static final TypeDescriptor OBJECT_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(Object.class);

    private static final TypeDescriptor LIST_STRING_TYPE_DESCRIPTOR = TypeDescriptor.collection(List.class,
            TypeDescriptor.valueOf(String.class));
    /**
     * 注册客户端存储库
     */
    private final RegisteredClientRepository registeredClientRepository;
    /**
     * 授权服务
     */
    private final OAuth2AuthorizationService authorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2TokenIntrospectionAuthenticationToken token = (OAuth2TokenIntrospectionAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = AuthenticationUtil.getAuthenticatedClientElseThrowInvalidClient(token);
        OAuth2Authorization authorization = this.authorizationService.findByToken(token.getToken(), null);
        if (authorization == null) {
            return token;
        }
        OAuth2Authorization.Token<OAuth2Token> authorizedToken = authorization
                .getToken(token.getToken());
        if (authorizedToken == null || !authorizedToken.isActive()) {
            return new OAuth2TokenIntrospectionAuthenticationToken(token.getToken(),
                    clientPrincipal, OAuth2TokenIntrospection.builder().build());
        }
        RegisteredClient authorizedClient = this.registeredClientRepository
                .findById(authorization.getRegisteredClientId());
        OAuth2TokenIntrospection tokenClaims = withActiveTokenClaims(authorization, authorizedToken, authorizedClient);

        return new OAuth2TokenIntrospectionAuthenticationToken(authorizedToken.getToken().getTokenValue(),
                clientPrincipal, tokenClaims);
    }

    private OAuth2TokenIntrospection withActiveTokenClaims(OAuth2Authorization authorization, OAuth2Authorization.Token<OAuth2Token> authorizedToken, RegisteredClient authorizedClient) {
        OAuth2TokenIntrospection.Builder tokenClaims;
        if (!CollectionUtils.isEmpty(authorizedToken.getClaims())) {
            Map<String, Object> claims = convertClaimsIfNecessary(authorizedToken.getClaims());
            tokenClaims = OAuth2TokenIntrospection.withClaims(claims).active(true);
        } else {
            tokenClaims = OAuth2TokenIntrospection.builder(true);
        }

        tokenClaims.clientId(authorizedClient.getClientId());

        tokenClaims.username(authorization.getPrincipalName());
        tokenClaims.subject(authorization.getPrincipalName());

        OAuth2Token token = authorizedToken.getToken();
        if (token.getIssuedAt() != null) {
            tokenClaims.issuedAt(token.getIssuedAt());
        }
        if (token.getExpiresAt() != null) {
            tokenClaims.expiresAt(token.getExpiresAt());
        }

        if (OAuth2AccessToken.class.isAssignableFrom(token.getClass())) {
            OAuth2AccessToken accessToken = (OAuth2AccessToken) token;
            tokenClaims.tokenType(accessToken.getTokenType().getValue());
        }

        return tokenClaims.build();
    }

    private Map<String, Object> convertClaimsIfNecessary(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = new HashMap<>(claims);

        Object value = claims.get(OAuth2TokenIntrospectionClaimNames.ISS);
        if (value != null && !(value instanceof URL)) {
            URL convertedValue = ClaimConversionService.getSharedInstance().convert(value, URL.class);
            if (convertedValue != null) {
                convertedClaims.put(OAuth2TokenIntrospectionClaimNames.ISS, convertedValue);
            }
        }

        value = claims.get(OAuth2TokenIntrospectionClaimNames.SCOPE);
        if (value != null && !(value instanceof List)) {
            Object convertedValue = ClaimConversionService.getSharedInstance()
                    .convert(value, OBJECT_TYPE_DESCRIPTOR, LIST_STRING_TYPE_DESCRIPTOR);
            if (convertedValue != null) {
                convertedClaims.put(OAuth2TokenIntrospectionClaimNames.SCOPE, convertedValue);
            }
        }

        value = claims.get(OAuth2TokenIntrospectionClaimNames.AUD);
        if (value != null && !(value instanceof List)) {
            Object convertedValue = ClaimConversionService.getSharedInstance()
                    .convert(value, OBJECT_TYPE_DESCRIPTOR, LIST_STRING_TYPE_DESCRIPTOR);
            if (convertedValue != null) {
                convertedClaims.put(OAuth2TokenIntrospectionClaimNames.AUD, convertedValue);
            }
        }

        return convertedClaims;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2TokenIntrospectionAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
