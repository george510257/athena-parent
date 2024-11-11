package com.gls.athena.starter.aliyun.oss.client;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

/**
 * 阿里云oss客户端配置
 *
 * @author george
 */
@AutoConfiguration
public class OssClientConfig {
    /**
     * ossClient 配置
     *
     * @param ossClientProperties oss配置
     * @return ossClient
     */
    @Bean
    @ConditionalOnMissingBean
    public OSS ossClient(OssClientProperties ossClientProperties) {
        if (ossClientProperties.getMode() == OssClientProperties.Mode.AK_SK) {
            return new OSSClientBuilder()
                    .build(ossClientProperties.getEndpoint(),
                            ossClientProperties.getToken().getAccessKeyId(),
                            ossClientProperties.getToken().getAccessKeySecret(),
                            ossClientProperties.getConfig());
        } else if (ossClientProperties.getMode() == OssClientProperties.Mode.STS) {
            return new OSSClientBuilder()
                    .build(ossClientProperties.getEndpoint(),
                            ossClientProperties.getStsToken().getAccessKeyId(),
                            ossClientProperties.getStsToken().getAccessKeySecret(),
                            ossClientProperties.getStsToken().getSecurityToken(),
                            ossClientProperties.getConfig());
        } else {
            throw new IllegalArgumentException("Unknown auth mode.");
        }
    }

    /**
     * acsClient 配置
     *
     * @param ossClientProperties oss配置
     * @return acsClient
     */
    @Bean
    @ConditionalOnMissingBean
    public IAcsClient acsClient(OssClientProperties ossClientProperties) {
        OssClientProperties.Acs acs = ossClientProperties.getAcs();
        DefaultProfile profile = DefaultProfile.getProfile(acs.getRegionId(),
                acs.getAccessKeyId(),
                acs.getAccessKeySecret());
        return new DefaultAcsClient(profile);
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
