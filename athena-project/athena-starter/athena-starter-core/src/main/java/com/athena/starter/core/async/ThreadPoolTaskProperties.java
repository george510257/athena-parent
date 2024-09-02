package com.athena.starter.core.async;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池任务属性
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".thread.pool.task")
public class ThreadPoolTaskProperties extends BaseProperties {
    /**
     * 核心线程数
     */
    private Integer corePoolSize = BaseConstants.CPU_NUM;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize = BaseConstants.CPU_NUM * 2;
    /**
     * 队列容量
     */
    private Integer queueCapacity = 5000;
    /**
     * 线程池终止前的最大等待时间
     */
    private Integer awaitTerminationSeconds = 60;
    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "ATHENA-THREAD-";
}
