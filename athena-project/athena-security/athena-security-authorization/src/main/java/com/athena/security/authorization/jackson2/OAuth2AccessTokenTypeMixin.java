package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

/**
 * oauth2 access token type mixin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OAuth2AccessTokenTypeMixin {
    @JsonCreator
    public OAuth2AccessTokenTypeMixin(@JsonProperty("value") String value) {
    }
}
