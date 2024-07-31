package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuth2RefreshTokenMixin {
    @JsonCreator
    public OAuth2RefreshTokenMixin(@JsonProperty("tokenValue") String tokenValue,
                                   @JsonProperty("issuedAt") Instant issuedAt,
                                   @JsonProperty("expiresAt") Instant expiresAt) {
    }
}
