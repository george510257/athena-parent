package com.athena.security.core.common.jackson2;

import com.fasterxml.jackson.annotation.*;

/**
 * CSRF令牌混合
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CsrfTokenMixin {

    /**
     * 构造函数
     *
     * @param headerName    头名称
     * @param parameterName 参数名称
     * @param token         令牌
     */
    @JsonCreator
    public CsrfTokenMixin(@JsonProperty("headerName") String headerName,
                          @JsonProperty("parameterName") String parameterName,
                          @JsonProperty("token") String token) {
    }
}
