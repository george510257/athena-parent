package com.gls.athena.common.bean.result;

import lombok.Getter;

/**
 * 返回结果异常
 *
 * @author george
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

    /**
     * 构造函数
     *
     * @param code    异常码
     * @param message 异常信息
     */
    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param code    异常码
     * @param message 异常信息
     * @param cause   异常原因
     */
    public ResultException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param code               异常码
     * @param message            异常信息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     */
    public ResultException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param enums 异常枚举
     */
    public ResultException(IResultStatus enums) {
        super(enums.getMessage());
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    /**
     * 构造函数
     *
     * @param enums 异常枚举
     * @param cause 异常原因
     */
    public ResultException(IResultStatus enums, Throwable cause) {
        super(enums.getMessage(), cause);
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    /**
     * 构造函数
     *
     * @param enums              异常枚举
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     */
    public ResultException(IResultStatus enums, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(enums.getMessage(), cause, enableSuppression, writableStackTrace);
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

}
