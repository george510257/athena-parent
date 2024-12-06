package com.gls.athena.starter.log.method;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * 方法日志事件
 *
 * @author george
 */
@Getter
public class MethodLogEvent extends MethodEvent {
    /**
     * 参数
     */
    private final List<Object> args;
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
     * 错误信息
     */
    private final String errorMessage;
    /**
     * 异常堆栈
     */
    private final String throwable;
    /**
     * 方法日志类型
     */
    private final MethodLogType type;
    /**
     * 跟踪ID
     */
    private final String traceId;

    /**
     * 构造方法
     *
     * @param source     源
     * @param methodLog  方法日志
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数
     * @param result     结果
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param throwable  异常
     * @param type       方法日志类型
     * @param traceId    跟踪ID
     */
    private MethodLogEvent(Object source, MethodLog methodLog, String className, String methodName, Object[] args, Object result, Date startTime, Date endTime, Throwable throwable, MethodLogType type, String traceId) {
        super(source, methodLog, className, methodName);
        this.args = CollUtil.newArrayList(args);
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.traceId = traceId;
        if (type == MethodLogType.ERROR && throwable != null) {
            this.errorMessage = throwable.getMessage();
            this.throwable = ExceptionUtil.stacktraceToString(throwable);
        } else {
            this.errorMessage = null;
            this.throwable = null;
        }
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
