package com.athena.common.bean.result;

import lombok.Getter;

/**
 * 返回结果异常
 */
@Getter
public class ResultException extends RuntimeException {
    /**
     * 异常码
     */
    private final Integer code;
    /**
     * 异常信息
     */
    private final String message;

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResultException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public ResultException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public ResultException(IResultStatus enums) {
        super(enums.getMessage());
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    public ResultException(IResultStatus enums, Throwable cause) {
        super(enums.getMessage(), cause);
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    public ResultException(IResultStatus enums, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(enums.getMessage(), cause, enableSuppression, writableStackTrace);
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

}
