package com.gls.athena.security.servlet.code.repository;

import cn.hutool.core.date.DateUtil;
import com.gls.athena.security.servlet.code.base.BaseCode;
import com.gls.athena.starter.data.redis.support.RedisUtil;

import java.util.concurrent.TimeUnit;

/**
 * 基于 Redis 的验证码存取器
 *
 * @author george
 */
public class RedisCodeRepository implements ICodeRepository {
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
    public void save(String key, BaseCode code) {
        RedisUtil.setCacheValue(buildKey(key), code, DateUtil.betweenMs(DateUtil.date(), code.getExpireTime()), TimeUnit.MILLISECONDS);
    }

    /**
     * 获取验证码
     *
     * @param key 验证码标识
     * @return 验证码
     */
    @Override
    public BaseCode get(String key) {
        return RedisUtil.getCacheValue(buildKey(key), BaseCode.class);
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
