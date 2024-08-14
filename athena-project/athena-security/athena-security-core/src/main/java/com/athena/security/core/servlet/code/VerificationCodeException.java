package com.athena.security.core.servlet.code;

/**
 * 验证码异常
 */
public class VerificationCodeException extends Exception {

    public VerificationCodeException() {
    }

    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationCodeException(Throwable cause) {
        super(cause);
    }
}
