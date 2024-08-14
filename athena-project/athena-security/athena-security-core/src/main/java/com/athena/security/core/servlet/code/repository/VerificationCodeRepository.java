package com.athena.security.core.servlet.code.repository;

import com.athena.security.core.servlet.code.base.VerificationCode;

/**
 * 验证码存储器
 */
public interface VerificationCodeRepository {

    /**
     * 保存验证码
     *
     * @param key  验证码标识
     * @param code 验证码
     */
    void save(String key, VerificationCode code);

    /**
     * 获取验证码
     *
     * @param key 验证码标识
     * @return 验证码
     */
    VerificationCode get(String key);

    /**
     * 移除验证码
     *
     * @param key 验证码标识
     */
    void remove(String key);

}
