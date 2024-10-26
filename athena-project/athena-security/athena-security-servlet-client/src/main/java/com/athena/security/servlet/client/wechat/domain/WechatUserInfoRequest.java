package com.athena.security.servlet.client.wechat.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信用户信息请求
 *
 * @author george
 */
@Data
public class WechatUserInfoRequest implements Serializable {
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 语言
     */
    private String lang = "zh_CN";
}
