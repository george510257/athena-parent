package com.athena.security.core.common.code;

import com.athena.security.core.common.code.image.ImageCodeGenerator;
import com.athena.security.core.common.code.image.ImageCodeSender;
import com.athena.security.core.common.code.repository.RedisVerificationCodeRepository;
import com.athena.security.core.common.code.repository.VerificationCodeRepository;
import com.athena.security.core.common.code.sms.SmsCodeGenerator;
import com.athena.security.core.common.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(VerificationCodeProperties.class)
public class VerificationCodeConfig {

    @Bean
    @ConditionalOnMissingBean
    public ImageCodeGenerator imageCodeGenerator(VerificationCodeProperties verificationCodeProperties) {
        return new ImageCodeGenerator(verificationCodeProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsCodeGenerator smsCodeGenerator(VerificationCodeProperties verificationCodeProperties) {
        return new SmsCodeGenerator(verificationCodeProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ImageCodeSender imageCodeSender() {
        return new ImageCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsCodeSender smsCodeSender() {
        return new SmsCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeRepository verificationCodeRepository() {
        return new RedisVerificationCodeRepository();
    }
}
