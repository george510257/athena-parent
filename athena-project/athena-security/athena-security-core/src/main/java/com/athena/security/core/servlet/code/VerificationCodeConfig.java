package com.athena.security.core.servlet.code;

import com.athena.security.core.servlet.code.image.ImageCodeGenerator;
import com.athena.security.core.servlet.code.image.ImageCodeProvider;
import com.athena.security.core.servlet.code.image.ImageCodeSender;
import com.athena.security.core.servlet.code.repository.RedisVerificationCodeRepository;
import com.athena.security.core.servlet.code.repository.VerificationCodeRepository;
import com.athena.security.core.servlet.code.sms.SmsCodeGenerator;
import com.athena.security.core.servlet.code.sms.SmsCodeProvider;
import com.athena.security.core.servlet.code.sms.SmsCodeSender;
import jakarta.annotation.Resource;
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
     * 验证码配置
     */
    @Resource
    private VerificationCodeProperties verificationCodeProperties;

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

    /**
     * 图片验证码提供器
     *
     * @param verificationCodeRepository 验证码存储器
     * @return 图片验证码提供器
     */
    @Bean
    @ConditionalOnMissingBean
    public ImageCodeProvider imageCodeProvider(VerificationCodeRepository verificationCodeRepository) {
        return (ImageCodeProvider) new ImageCodeProvider()
                .setImage(verificationCodeProperties.getImage())
                .setRepository(verificationCodeRepository)
                .setGenerator(new ImageCodeGenerator()
                        .setImage(verificationCodeProperties.getImage()))
                .setSender(new ImageCodeSender());
    }

    /**
     * 短信验证码提供器
     *
     * @param verificationCodeRepository 验证码存储器
     * @return 短信验证码提供器
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsCodeProvider smsCodeProvider(VerificationCodeRepository verificationCodeRepository) {
        return (SmsCodeProvider) new SmsCodeProvider()
                .setSms(verificationCodeProperties.getSms())
                .setRepository(verificationCodeRepository)
                .setGenerator(new SmsCodeGenerator()
                        .setSms(verificationCodeProperties.getSms()))
                .setSender(new SmsCodeSender());
    }

    /**
     * 验证码过滤器
     *
     * @param verificationCodeManager 验证码管理器
     * @return 验证码过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeFilter verificationCodeFilter(VerificationCodeManager verificationCodeManager) {
        return new VerificationCodeFilter()
                .setVerificationCodeManager(verificationCodeManager);
    }
}
