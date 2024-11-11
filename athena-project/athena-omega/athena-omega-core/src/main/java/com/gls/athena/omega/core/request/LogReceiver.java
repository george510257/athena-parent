package com.gls.athena.omega.core.request;

/**
 * 日志接收器
 *
 * @author george
 */
@FunctionalInterface
public interface LogReceiver {

    /**
     * 接收日志
     *
     * @param targetClass 目标类
     * @param type        类型
     * @param message     消息
     * @param throwable   异常
     */
    void receive(Class<?> targetClass, String type, String message, Throwable throwable);
}
