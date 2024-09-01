package com.athena.starter.core.async;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 *
 * @author george
 */
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    /**
     * 线程池配置
     */
    @Resource
    private ThreadPoolTaskProperties properties;

    /**
     * 获取异步线程池
     *
     * @return Executor 异步线程池
     */
    @Bean
    @ConditionalOnMissingBean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程大小 默认区 CPU 数量
        executor.setCorePoolSize(properties.getCorePoolSize());
        // 最大线程大小 默认区 CPU * 2 数量
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        // 队列最大容量
        executor.setQueueCapacity(properties.getQueueCapacity());
        // 拒绝策略 默认为 CallerRunsPolicy 策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时长
        executor.setAwaitTerminationSeconds(properties.getAwaitTerminationSeconds());
        // 线程名称前缀
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        return executor;
    }
}
