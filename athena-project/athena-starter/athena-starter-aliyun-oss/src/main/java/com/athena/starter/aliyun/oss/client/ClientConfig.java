package com.athena.starter.aliyun.oss.client;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

/**
 * 阿里云oss客户端配置
 */
@AutoConfiguration
public class ClientConfig {
    /**
     * ossClient 配置
     *
     * @param clientProperties oss配置
     * @return ossClient
     */
    @Bean
    @ConditionalOnMissingBean
    public OSS ossClient(ClientProperties clientProperties) {
        if (clientProperties.getMode() == ClientProperties.Mode.AK_SK) {
            return new OSSClientBuilder()
                    .build(clientProperties.getEndpoint(),
                            clientProperties.getToken().getAccessKeyId(),
                            clientProperties.getToken().getAccessKeySecret(),
                            clientProperties.getConfig());
        } else if (clientProperties.getMode() == ClientProperties.Mode.STS) {
            return new OSSClientBuilder()
                    .build(clientProperties.getEndpoint(),
                            clientProperties.getStsToken().getAccessKeyId(),
                            clientProperties.getStsToken().getAccessKeySecret(),
                            clientProperties.getStsToken().getSecurityToken(),
                            clientProperties.getConfig());
        } else {
            throw new IllegalArgumentException("Unknown auth mode.");
        }
    }

    /**
     * 关闭ossClient
     *
     * @param event 事件
     */
    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent event) {
        // 关闭ossClient
        Map<String, OSS> beansOfType = event.getApplicationContext().getBeansOfType(OSS.class);
        beansOfType.values().forEach(OSS::shutdown);
    }
}
