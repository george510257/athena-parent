package com.athena.security.core.servlet.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 */
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
