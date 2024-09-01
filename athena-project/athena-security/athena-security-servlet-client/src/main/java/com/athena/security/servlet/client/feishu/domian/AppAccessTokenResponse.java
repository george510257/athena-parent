package com.athena.security.servlet.client.feishu.domian;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用访问令牌响应
 *
 * @author george
 */
@Data
public class AppAccessTokenResponse implements Serializable {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 应用访问令牌
     */
    @JsonProperty("app_access_token")
    private String appAccessToken;
    /**
     * 过期时间
     */
    private Integer expire;
    /**
     * 租户访问令牌
     */
    @JsonProperty("tenant_access_token")
    private String tenantAccessToken;
}
