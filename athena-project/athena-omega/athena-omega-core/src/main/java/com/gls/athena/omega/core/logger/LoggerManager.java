package com.gls.athena.omega.core.logger;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

/**
 * 日志管理器
 *
 * @author george
 */
@Data
@Accessors(chain = true)
public class LoggerManager {
    /**
     * 日志消费者
     */
    private Consumer<BaseLogger> loggerConsumer;
    /**
     * 调试模式
     */
    private boolean debugMode = false;

    /**
     * info日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @return 日志管理器
     */
    public LoggerManager info(Class<?> targetClass, String message) {
        if (loggerConsumer != null) {
            loggerConsumer.accept(new InfoLogger().setTargetClass(targetClass).setMessage(message));
        }
        return this;
    }

    /**
     * warn日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @return 日志管理器
     */
    public LoggerManager warn(Class<?> targetClass, String message) {
        if (loggerConsumer != null) {
            loggerConsumer.accept(new WarnLogger().setTargetClass(targetClass).setMessage(message));
        }
        return this;
    }

    /**
     * success日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @return 日志管理器
     */
    public LoggerManager success(Class<?> targetClass, String message) {
        if (loggerConsumer != null) {
            loggerConsumer.accept(new SuccessLogger().setTargetClass(targetClass).setMessage(message));
        }
        return this;
    }

    /**
     * error日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @return 日志管理器
     */
    public LoggerManager error(Class<?> targetClass, String message) {
        return error(targetClass, message, null);
    }

    /**
     * error日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @param throwable   异常
     * @return 日志管理器
     */
    public LoggerManager error(Class<?> targetClass, String message, Throwable throwable) {
        if (loggerConsumer != null) {
            ErrorLogger errorLogger = new ErrorLogger();
            if (throwable != null) {
                errorLogger.setThrowable(throwable);
            }
            errorLogger.setTargetClass(targetClass).setMessage(message);
            loggerConsumer.accept(errorLogger);
        }
        return this;
    }

    /**
     * debug日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @return 日志管理器
     */
    public LoggerManager debug(Class<?> targetClass, String message) {
        return debug(targetClass, message, null);
    }

    /**
     * debug日志
     *
     * @param targetClass 目标类
     * @param message     消息
     * @param trace       堆栈信息
     * @return 日志管理器
     */
    public LoggerManager debug(Class<?> targetClass, String message, StackTraceElement[] trace) {
        if (debugMode && loggerConsumer != null) {
            DebugLogger debugLogger = new DebugLogger();
            if (trace != null) {
                debugLogger.setTrace(trace);
            }
            debugLogger.setTargetClass(targetClass).setMessage(message);
            loggerConsumer.accept(debugLogger);
        }
        return this;
    }

}
