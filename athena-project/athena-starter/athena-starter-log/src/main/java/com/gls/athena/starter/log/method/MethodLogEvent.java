package com.gls.athena.starter.log.method;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * 方法日志事件
 *
 * @author george
 */
@Getter
public class MethodLogEvent extends ApplicationEvent {
    /**
     * 编码
     */
    private final String code;
    /**
     * 名称
     */
    private final String name;
    /**
     * 描述
     */
    private final String description;
    /**
     * 类名
     */
    private final String className;
    /**
     * 方法名
     */
    private final String methodName;
    /**
     * 参数
     */
    private final Object[] args;
    /**
     * 结果
     */
    private final Object result;
    /**
     * 开始时间
     */
    private final Date startTime;
    /**
     * 结束时间
     */
    private final Date endTime;
    /**
     * 异常
     */
    private final Throwable throwable;
    /**
     * 方法日志类型
     */
    private final MethodLogType methodLogType;
    /**
     * 跟踪ID
     */
    private final String traceId;

    /**
     * 构造方法
     *
     * @param source        源
     * @param methodLog     方法日志
     * @param className     类名
     * @param methodName    方法名
     * @param args          参数
     * @param result        结果
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param throwable     异常
     * @param methodLogType 方法日志类型
     * @param traceId       跟踪ID
     */
    private MethodLogEvent(Object source, MethodLog methodLog, String className, String methodName, Object[] args, Object result, Date startTime, Date endTime, Throwable throwable, MethodLogType methodLogType, String traceId) {
        super(source);
        this.code = methodLog.code();
        this.name = methodLog.name();
        this.description = methodLog.description();
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
        this.throwable = throwable;
        this.methodLogType = methodLogType;
        this.traceId = traceId;
    }

    /**
     * 创建正常方法日志事件
     *
     * @param source     源
     * @param methodLog  方法日志
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数
     * @param result     结果
     * @param startTime  开始时间
     * @param traceId    跟踪ID
     * @return 方法日志事件
     */
    public static MethodLogEvent ofNormal(Object source, MethodLog methodLog, String className, String methodName, Object[] args, Object result, Date startTime, String traceId) {
        return new MethodLogEvent(source, methodLog, className, methodName, args, result, startTime, new Date(), null, MethodLogType.NORMAL, traceId);
    }

    /**
     * 创建异常方法日志事件
     *
     * @param source     源
     * @param methodLog  方法日志
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数
     * @param throwable  异常
     * @param startTime  开始时间
     * @param traceId    跟踪ID
     * @return 方法日志事件
     */
    public static MethodLogEvent ofError(Object source, MethodLog methodLog, String className, String methodName, Object[] args, Throwable throwable, Date startTime, String traceId) {
        return new MethodLogEvent(source, methodLog, className, methodName, args, null, startTime, new Date(), throwable, MethodLogType.ERROR, traceId);
    }
}
