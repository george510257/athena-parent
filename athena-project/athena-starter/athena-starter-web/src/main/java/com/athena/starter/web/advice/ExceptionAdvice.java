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

@Slf4j
@RestControllerAdvice(basePackages = BaseConstants.BASE_PACKAGE_PREFIX)
public class ExceptionAdvice {

    @ExceptionHandler(ResultException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> resultExceptionHandler(ResultException e) {
        log.error("ResultException: {}", e.getMessage(), e);
        return ResultStatus.FAIL.toResult().setCode(e.getCode()).setMessage(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResultStatus.SERVER_ERROR.toResult().setMessage(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> exceptionHandler(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ResultStatus.SERVER_ERROR.toResult().setMessage(e.getMessage());
    }
}
