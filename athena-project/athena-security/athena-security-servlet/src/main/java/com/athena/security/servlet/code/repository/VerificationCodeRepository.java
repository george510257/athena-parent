package com.athena.security.servlet.code.repository;

import com.athena.security.servlet.code.base.BaseCode;

/**
 * 验证码存储器
 *
 * @author george
 */
public interface VerificationCodeRepository {

    /**
     * 保存验证码
     *
     * @param key  验证码标识
     * @param code 验证码
     */
    void save(String key, BaseCode code);

    /**
     * 获取验证码
     *
     * @param key 验证码标识
     * @return 验证码
     */
    BaseCode get(String key);

    /**
     * 移除验证码
     *
     * @param key 验证码标识
     */
    void remove(String key);

}
