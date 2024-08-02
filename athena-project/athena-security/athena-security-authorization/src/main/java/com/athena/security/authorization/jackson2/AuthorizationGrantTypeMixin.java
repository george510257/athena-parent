package com.athena.security.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

/**
 * oauth2授权授予类型混合
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AuthorizationGrantTypeMixin {
    /**
     * 构造函数
     *
     * @param value 值
     */
    @JsonCreator
    public AuthorizationGrantTypeMixin(@JsonProperty("value") String value) {
    }
}
