package com.athena.security.servlet.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author george
 */
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
