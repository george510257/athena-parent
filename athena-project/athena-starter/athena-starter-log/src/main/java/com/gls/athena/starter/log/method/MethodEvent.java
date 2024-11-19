package com.gls.athena.starter.log.method;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 方法日志事件
 *
 * @author george
 */
@Getter
public class MethodEvent extends ApplicationEvent {
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
     * 构造方法
     *
     * @param source     源
     * @param methodLog  方法日志
     * @param className  类名
     * @param methodName 方法名
     */
    public MethodEvent(Object source, MethodLog methodLog, String className, String methodName) {
        super(source);
        this.code = methodLog.code();
        this.name = methodLog.name();
        this.description = methodLog.description();
        this.className = className;
        this.methodName = methodName;
    }

}
