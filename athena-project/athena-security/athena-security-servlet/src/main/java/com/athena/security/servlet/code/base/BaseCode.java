package com.athena.security.servlet.code.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码
 */
@Data
public abstract class BaseCode implements Serializable {
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expireTime;
}
