package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

import java.time.Instant;

/**
 * oauth2 authorization code mixin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OAuth2AuthorizationCodeMixin {

    @JsonCreator
    public OAuth2AuthorizationCodeMixin(@JsonProperty("tokenValue") String tokenValue,
                                        @JsonProperty("issuedAt") Instant issuedAt,
                                        @JsonProperty("expiresAt") Instant expiresAt) {
    }
}
