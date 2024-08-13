package com.athena.security.core.common.code.base;

/**
 * 验证码生成器
 *
 * @param <VC> 验证码类型
 */
@FunctionalInterface
public interface VerificationCodeGenerator<VC extends VerificationCode> {

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    VC generate();
}
