package com.gls.athena.starter.aliyun.sms.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信客户端配置
 *
 * @author george
 */
@Configuration
public class SmsClientConfig {

    @Bean
    public Client smsClient(SmsClientProperties smsClientProperties) throws Exception {
        Config config = new Config()
                .setAccessKeyId(smsClientProperties.getAccessKeyId())
                .setAccessKeySecret(smsClientProperties.getAccessKeySecret())
                .setEndpoint(smsClientProperties.getEndpoint());
        return new Client(config);
    }
}
