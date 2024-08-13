package com.athena.security.core.common.code.base;

/**
 * 验证码发送器
 *
 * @param <V> 验证码类型
 */
@FunctionalInterface
public interface VerificationCodeSender<V extends VerificationCode> {

    /**
     * 发送验证码
     *
     * @param target 接收目标
     * @param code   验证码
     */
    void send(String target, V code);
}
