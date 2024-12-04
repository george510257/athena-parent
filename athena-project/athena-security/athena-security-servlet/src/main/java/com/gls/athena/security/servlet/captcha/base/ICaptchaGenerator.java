package com.gls.athena.security.servlet.captcha.base;

/**
 * 验证码生成器
 *
 * @param <Captcha> 验证码类型
 * @author george
 */
@FunctionalInterface
public interface ICaptchaGenerator<Captcha extends BaseCaptcha> {

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    Captcha generate();
}
