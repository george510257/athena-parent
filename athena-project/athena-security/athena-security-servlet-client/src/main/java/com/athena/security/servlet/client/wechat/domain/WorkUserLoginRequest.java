package com.athena.security.servlet.client.wechat.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 企业微信用户登录请求
 *
 * @author george
 */
@Data
public class WorkUserLoginRequest implements Serializable {
    /**
     * 企业微信登录凭证
     */
    private String code;
    /**
     * 企业微信登录凭证
     */
    private String accessToken;
}
