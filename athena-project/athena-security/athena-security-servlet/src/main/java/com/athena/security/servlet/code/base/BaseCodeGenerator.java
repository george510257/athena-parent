package com.athena.security.servlet.code.base;

/**
 * 验证码生成器
 *
 * @param <Code> 验证码类型
 */
@FunctionalInterface
public interface BaseCodeGenerator<Code extends BaseCode> {

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    Code generate();
}
