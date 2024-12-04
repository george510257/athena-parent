package com.gls.athena.security.servlet.captcha.base;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 验证码发送器
 *
 * @param <Captcha> 验证码类型
 * @author george
 */
@FunctionalInterface
public interface ICaptchaSender<Captcha extends BaseCaptcha> {

    /**
     * 发送验证码
     *
     * @param target   接收目标
     * @param captcha  验证码
     * @param response 响应
     */
    void send(String target, Captcha captcha, HttpServletResponse response);
}
