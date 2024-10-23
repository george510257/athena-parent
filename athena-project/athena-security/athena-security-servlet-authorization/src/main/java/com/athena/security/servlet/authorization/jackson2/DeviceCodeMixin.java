package com.athena.security.servlet.authorization.jackson2;

import com.fasterxml.jackson.annotation.*;

import java.time.Instant;

/**
 * OAuth2 设备代码混合
 *
 * @author george
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceCodeMixin {

    /**
     * 构造函数
     *
     * @param tokenValue 令牌值
     * @param issuedAt   发布时间
     * @param expiresAt  过期时间
     */
    @JsonCreator
    public DeviceCodeMixin(@JsonProperty("tokenValue") String tokenValue,
                           @JsonProperty("issuedAt") Instant issuedAt,
                           @JsonProperty("expiresAt") Instant expiresAt) {
    }

}
