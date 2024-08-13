package com.athena.security.core.common;

import com.athena.security.core.common.code.base.VerificationCodeGenerator;
import com.athena.security.core.common.code.base.VerificationCodeSender;
import com.athena.security.core.common.code.image.ImageCode;
import com.athena.security.core.common.code.image.ImageCodeGenerator;
import com.athena.security.core.common.code.image.ImageCodeSender;
import com.athena.security.core.common.code.repository.RedisVerificationCodeRepository;
import com.athena.security.core.common.code.repository.VerificationCodeRepository;
import com.athena.security.core.common.code.sms.SmsCode;
import com.athena.security.core.common.code.sms.SmsCodeGenerator;
import com.athena.security.core.common.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerificationCodeConfig {

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeGenerator<ImageCode> imageCodeGenerator(SecurityProperties securityProperties) {
        return new ImageCodeGenerator(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeGenerator<SmsCode> smsCodeGenerator(SecurityProperties securityProperties) {
        return new SmsCodeGenerator(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeSender<ImageCode> imageCodeSender() {
        return new ImageCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeSender<SmsCode> smsCodeSender() {
        return new SmsCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeRepository verificationCodeRepository() {
        return new RedisVerificationCodeRepository();
    }
}
