package com.gls.athena.starter.aliyun.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gls.athena.starter.aliyun.oss.endpoint.OssEndpoint;
import com.gls.athena.starter.aliyun.oss.support.OssProtocolResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss配置
 *
 * @author george
 */
@Configuration
public class AliyunOssConfig {
    /**
     * 阿里云oss客户端
     *
     * @param properties 阿里云oss配置
     * @return 阿里云oss客户端
     */
    @Bean
    @ConditionalOnMissingBean
    public OSS aliyunOssClient(AliyunOssProperties properties) {
        if (AliyunOssProperties.AuthType.AS_AK.equals(properties.getAuthType())) {
            return new OSSClientBuilder().build(properties.getEndpoint(),
                    properties.getAccessKeyId(), properties.getAccessKeySecret(), properties.getConfig());
        }
        if (AliyunOssProperties.AuthType.STS.equals(properties.getAuthType())) {
            return new OSSClientBuilder().build(properties.getEndpoint(),
                    properties.getAccessKeyId(), properties.getAccessKeySecret(), properties.getSecurityToken(), properties.getConfig());
        }
        throw new IllegalArgumentException("Unknown auth type: " + properties.getAuthType());
    }

    /**
     * oss协议解析器
     *
     * @return oss协议解析器
     */
    @Bean
    @ConditionalOnMissingBean
    public OssProtocolResolver ossProtocolResolver() {
        return new OssProtocolResolver();
    }

    /**
     * oss端点
     *
     * @return oss端点
     */
    @Bean
    @ConditionalOnMissingBean
    public OssEndpoint ossEndpoint() {
        return new OssEndpoint();
    }
}
