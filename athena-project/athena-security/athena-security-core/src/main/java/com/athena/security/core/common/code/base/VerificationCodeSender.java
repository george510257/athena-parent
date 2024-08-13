package com.athena.security.core.common.code.base;

/**
 * 验证码发送器
 *
 * @param <VC> 验证码类型
 */
@FunctionalInterface
public interface VerificationCodeSender<VC extends VerificationCode> {

    /**
     * 发送验证码
     *
     * @param target 接收目标
     * @param code   验证码
     */
    void send(String target, VC code);
}
