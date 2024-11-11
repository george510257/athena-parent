package com.gls.athena.security.servlet.client.feishu.domian;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用访问令牌请求
 *
 * @author george
 */
@Data
public class FeishuAppAccessTokenRequest implements Serializable {
    /**
     * 应用 ID
     */
    @JsonProperty("app_id")
    private String appId;
    /**
     * 应用密钥
     */
    @JsonProperty("app_secret")
    private String appSecret;
}
