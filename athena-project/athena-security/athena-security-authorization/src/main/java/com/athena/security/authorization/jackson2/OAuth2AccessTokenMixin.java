package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.util.Set;

/**
 * oauth2 access token mixin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OAuth2AccessTokenMixin {

    @JsonCreator
    public OAuth2AccessTokenMixin(@JsonProperty("tokenType") OAuth2AccessToken.TokenType tokenType,
                                  @JsonProperty("tokenValue") String tokenValue,
                                  @JsonProperty("issuedAt") Instant issuedAt,
                                  @JsonProperty("expiresAt") Instant expiresAt,
                                  @JsonProperty("scopes") Set<String> scopes) {
    }
}
