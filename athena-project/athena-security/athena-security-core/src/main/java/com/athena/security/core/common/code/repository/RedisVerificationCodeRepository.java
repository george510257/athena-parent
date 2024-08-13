package com.athena.security.core.common.code.repository;

import com.athena.security.core.common.code.base.VerificationCode;
import com.athena.starter.data.redis.support.RedisUtil;

/**
 * 基于 Redis 的验证码存取器
 */
public class RedisVerificationCodeRepository implements VerificationCodeRepository {
    /**
     * 缓存名称
     */
    private static final String CACHE_NAME = "verification-code";

    /**
     * 保存验证码
     *
     * @param key  验证码标识
     * @param code 验证码
     */
    @Override
    public void save(String key, VerificationCode code) {
        RedisUtil.setCacheValue(buildKey(key), code);
    }

    /**
     * 获取验证码
     *
     * @param key 验证码标识
     * @return 验证码
     */
    @Override
    public VerificationCode get(String key) {
        return RedisUtil.getCacheValue(buildKey(key), VerificationCode.class);
    }

    /**
     * 移除验证码
     *
     * @param key 验证码标识
     */
    @Override
    public void remove(String key) {
        RedisUtil.deleteCacheValue(buildKey(key));
    }

    /**
     * 构建缓存键
     *
     * @param key 键
     * @return 缓存键
     */
    private String buildKey(String key) {
        return CACHE_NAME + RedisUtil.SEPARATOR + key;
    }
}
