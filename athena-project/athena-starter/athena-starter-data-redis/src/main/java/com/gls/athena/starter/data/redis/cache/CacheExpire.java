package com.gls.athena.starter.data.redis.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存时间
 *
 * @author george
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheExpire {

    /**
     * 过期时间
     *
     * @return 过期时间
     */
    long timeToLive();

    /**
     * 时间单位
     *
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
