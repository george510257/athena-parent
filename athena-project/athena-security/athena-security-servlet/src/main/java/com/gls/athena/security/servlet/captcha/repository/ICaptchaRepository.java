package com.gls.athena.security.servlet.captcha.repository;

import com.gls.athena.security.servlet.captcha.base.BaseCaptcha;

/**
 * 验证码存储器
 *
 * @author george
 */
public interface ICaptchaRepository {

    /**
     * 保存验证码
     *
     * @param key     验证码标识
     * @param captcha 验证码
     */
    void save(String key, BaseCaptcha captcha);

    /**
     * 获取验证码
     *
     * @param key 验证码标识
     * @return 验证码
     */
    BaseCaptcha get(String key);

    /**
     * 移除验证码
     *
     * @param key 验证码标识
     */
    void remove(String key);

}
