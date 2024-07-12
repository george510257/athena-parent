package com.athena.starter.data.redis.cache;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 默认缓存配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".cache")
public class DefaultCacheProperties extends BaseProperties {
    /**
     * 缓存过期时间
     */
    private Map<String, CacheExpire> expires;

    /**
     * 缓存过期配置
     */
    @Data
    public static class CacheExpire implements Serializable {
        /**
         * 过期时间
         */
        private Long timeToLive;

        /**
         * 时间单位
         */
        private TimeUnit timeUnit;
    }
}