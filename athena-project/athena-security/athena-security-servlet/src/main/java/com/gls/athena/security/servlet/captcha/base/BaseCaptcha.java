package com.gls.athena.security.servlet.captcha.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码
 *
 * @author george
 */
@Data
public abstract class BaseCaptcha implements Serializable {
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expireTime;
}
