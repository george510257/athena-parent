package com.gls.athena.starter.log.method;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 系统日志切面
 *
 * @author george
 */
@Aspect
@Slf4j
@Component
public class MethodLogAspect {
    /**
     * 事件发布器
     */
    @Resource
    private ApplicationEventPublisher publisher;
    /**
     * 跟踪器
     */
    @Resource
    private Tracer tracer;
    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 环绕通知
     *
     * @param point     切点
     * @param methodLog 方法日志注解
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(methodLog)")
    public Object around(ProceedingJoinPoint point, MethodLog methodLog) throws Throwable {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        String traceId = this.getTraceId();
        log.debug("[类名]:{},[方法]:{}", className, methodName);
        Object[] args = point.getArgs();
        log.debug("方法参数：{}", args);
        Date startTime = new Date();
        log.debug("方法开始时间：{}", startTime);
        try {
            Object result = point.proceed();
            log.debug("方法执行结果：{}", result);
            log.debug("方法执行时间：{}ms", System.currentTimeMillis() - startTime.getTime());
            publisher.publishEvent(MethodLogEvent.ofNormal(this, methodLog, applicationName, className, methodName, args, result, startTime, traceId));
            return result;
        } catch (Throwable throwable) {
            log.error("方法执行异常：{}", throwable.getMessage(), throwable);
            publisher.publishEvent(MethodLogEvent.ofError(this, methodLog, applicationName, className, methodName, args, throwable, startTime, traceId));
            throw throwable;
        }
    }

    /**
     * 获取traceId
     *
     * @return traceId
     */
    private String getTraceId() {
        Span span = tracer.currentSpan();
        if (span != null) {
            return span.context().traceId();
        }
        return "";
    }
}
