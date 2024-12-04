package com.gls.athena.security.servlet.captcha;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author george
 */
public class CaptchaAuthenticationException extends AuthenticationException {

    public CaptchaAuthenticationException(String message) {
        super(message);
    }

    public CaptchaAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
