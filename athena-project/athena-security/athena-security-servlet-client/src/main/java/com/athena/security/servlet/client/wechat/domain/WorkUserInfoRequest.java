package com.athena.security.servlet.client.wechat.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 企业微信用户信息请求
 *
 * @author george
 */
@Data
public class WorkUserInfoRequest implements Serializable {
    /**
     * 企业微信登录凭证
     */
    private String accessToken;
    /**
     * 成员userid
     */
    private String userid;
}
