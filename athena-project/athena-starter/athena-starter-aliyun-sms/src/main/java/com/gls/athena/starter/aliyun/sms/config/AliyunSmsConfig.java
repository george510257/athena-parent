package com.gls.athena.starter.aliyun.sms.config;

import com.aliyuncs.IAcsClient;
import com.gls.athena.starter.aliyun.core.support.AliyunHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信客户端配置
 *
 * @author george
 */
@Configuration
public class AliyunSmsConfig {

    /**
     * 创建短信客户端
     *
     * @param aliyunSmsProperties 短信配置
     * @return IAcsClient
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsAcsClient")
    public IAcsClient smsAcsClient(AliyunSmsProperties aliyunSmsProperties) {
        return AliyunHelper.createAcsClient(aliyunSmsProperties);
    }
}
