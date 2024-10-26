package com.athena.security.servlet.client.wechat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业微信访问令牌响应
 *
 * @author george
 */
@Data
public class WorkAccessTokenResponse implements Serializable {
    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private Integer errCode;
    /**
     * 错误消息
     */
    @JsonProperty("errmsg")
    private String errMsg;
    /**
     * 访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 有效时间
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
}
