package com.athena.starter.web.advice;

import com.athena.common.bean.result.Result;
import com.athena.common.bean.result.ResultException;
import com.athena.common.bean.result.ResultStatus;
import com.athena.common.core.constant.BaseConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常通知
 */
@Slf4j
@RestControllerAdvice(basePackages = BaseConstants.BASE_PACKAGE_PREFIX)
public class ExceptionAdvice {

    /**
     * 处理ResultException异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(ResultException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> resultExceptionHandler(ResultException e) {
        log.error("ResultException: {}", e.getMessage(), e);
        return ResultStatus.FAIL.toResult().setCode(e.getCode()).setMessage(e.getMessage());
    }

    /**
     * 处理RuntimeException异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResultStatus.SERVER_ERROR.toResult().setMessage(e.getMessage());
    }

    /**
     * 处理Exception异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> exceptionHandler(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ResultStatus.SERVER_ERROR.toResult().setMessage(e.getMessage());
    }
}
