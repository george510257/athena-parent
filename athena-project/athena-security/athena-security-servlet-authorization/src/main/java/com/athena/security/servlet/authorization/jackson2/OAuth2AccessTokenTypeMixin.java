package com.athena.security.servlet.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

/**
 * oauth2访问令牌类型混合
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OAuth2AccessTokenTypeMixin {
    /**
     * 构造函数
     *
     * @param value 值
     */
    @JsonCreator
    public OAuth2AccessTokenTypeMixin(@JsonProperty("value") String value) {
    }
}
