package com.gls.athena.security.servlet.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author george
 */
public class CodeAuthenticationException extends AuthenticationException {

    public CodeAuthenticationException(String message) {
        super(message);
    }

    public CodeAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
