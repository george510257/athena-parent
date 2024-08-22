package com.athena.security.servlet.code.base;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 验证码发送器
 *
 * @param <Code> 验证码类型
 */
@FunctionalInterface
public interface BaseCodeSender<Code extends BaseCode> {

    /**
     * 发送验证码
     *
     * @param target   接收目标
     * @param code     验证码
     * @param response 响应
     */
    void send(String target, Code code, HttpServletResponse response);
}
