package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

import java.time.Instant;
import java.util.Map;

/**
 * OAuth2 oidc id token mixin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OidcIdTokenMixin {

    @JsonCreator
    public OidcIdTokenMixin(@JsonProperty("tokenValue") String tokenValue,
                            @JsonProperty("issuedAt") Instant issuedAt,
                            @JsonProperty("expiresAt") Instant expiresAt,
                            @JsonProperty("claims") Map<String, Object> claims) {
    }
}
