package com.athena.security.core.common.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 */
public class VerificationCodeException extends AuthenticationException {
    /**
     * @param msg   异常信息
     * @param cause 异常
     */
    public VerificationCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * @param msg 异常信息
     */
    public VerificationCodeException(String msg) {
        super(msg);
    }
}
