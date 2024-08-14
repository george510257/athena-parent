package com.athena.security.core.servlet.code;

import com.athena.security.core.servlet.code.image.ImageCodeGenerator;
import com.athena.security.core.servlet.code.image.ImageCodeSender;
import com.athena.security.core.servlet.code.repository.RedisVerificationCodeRepository;
import com.athena.security.core.servlet.code.repository.VerificationCodeRepository;
import com.athena.security.core.servlet.code.sms.SmsCodeGenerator;
import com.athena.security.core.servlet.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 */
@Configuration
@EnableConfigurationProperties(VerificationCodeProperties.class)
public class VerificationCodeConfig {

    /**
     * 图片验证码生成器
     *
     * @param verificationCodeProperties 验证码配置
     * @return 图片验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public ImageCodeGenerator imageCodeGenerator(VerificationCodeProperties verificationCodeProperties) {
        return new ImageCodeGenerator(verificationCodeProperties);
    }

    /**
     * 短信验证码生成器
     *
     * @param verificationCodeProperties 验证码配置
     * @return 短信验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsCodeGenerator smsCodeGenerator(VerificationCodeProperties verificationCodeProperties) {
        return new SmsCodeGenerator(verificationCodeProperties);
    }

    /**
     * 图片验证码发送器
     *
     * @return 图片验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean
    public ImageCodeSender imageCodeSender() {
        return new ImageCodeSender();
    }

    /**
     * 短信验证码发送器
     *
     * @return 短信验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsCodeSender smsCodeSender() {
        return new SmsCodeSender();
    }

    /**
     * 验证码存储器
     *
     * @return 验证码存储器
     */
    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeRepository verificationCodeRepository() {
        return new RedisVerificationCodeRepository();
    }

}
