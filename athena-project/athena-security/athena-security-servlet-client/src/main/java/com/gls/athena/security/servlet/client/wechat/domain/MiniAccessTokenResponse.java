package com.gls.athena.security.servlet.client.wechat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用访问令牌响应
 *
 * @author George
 */
@Data
public class MiniAccessTokenResponse implements Serializable {
    /**
     * 应用访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 过期时间
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
}
