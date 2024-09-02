package com.athena.starter.aliyun.oss.storage;

import com.aliyun.oss.OSS;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;

/**
 * 对象存储配置
 *
 * @author george
 */
@AutoConfiguration
public class OssStorageConfig {

    /**
     * 对象存储协议解析器
     *
     * @param oss      oss客户端
     * @param executor oss任务执行器
     * @return 对象存储协议解析器
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({OSS.class, Executor.class})
    public OssStorageProtocolResolver ossStorageProtocolResolver(OSS oss, Executor executor) {
        return new OssStorageProtocolResolver(oss, executor);
    }
}
