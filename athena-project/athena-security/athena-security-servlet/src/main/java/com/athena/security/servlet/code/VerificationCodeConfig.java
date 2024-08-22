package com.athena.security.servlet.code;

import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.code.image.ImageCodeGenerator;
import com.athena.security.servlet.code.image.ImageCodeProvider;
import com.athena.security.servlet.code.image.ImageCodeSender;
import com.athena.security.servlet.code.repository.RedisVerificationCodeRepository;
import com.athena.security.servlet.code.repository.VerificationCodeRepository;
import com.athena.security.servlet.code.sms.SmsCodeGenerator;
import com.athena.security.servlet.code.sms.SmsCodeProvider;
import com.athena.security.servlet.code.sms.SmsCodeSender;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 验证码配置
 */
@Configuration
public class VerificationCodeConfig {
    /**
     * 验证码配置
     */
    @Resource
    private CoreSecurityProperties coreSecurityProperties;

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
        return (ImageCodeProvider) new ImageCodeProvider(coreSecurityProperties)
                .setRepository(verificationCodeRepository)
                .setGenerator(new ImageCodeGenerator(coreSecurityProperties))
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
        return (SmsCodeProvider) new SmsCodeProvider(coreSecurityProperties)
                .setRepository(verificationCodeRepository)
                .setGenerator(new SmsCodeGenerator(coreSecurityProperties))
                .setSender(new SmsCodeSender());
    }

    /**
     * 验证码过滤器
     *
     * @param verificationCodeManager      验证码管理器
     * @param authenticationFailureHandler 认证失败处理器
     * @return 验证码过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeFilter verificationCodeFilter(VerificationCodeManager verificationCodeManager,
                                                         AuthenticationFailureHandler authenticationFailureHandler) {
        return new VerificationCodeFilter()
                .setVerificationCodeManager(verificationCodeManager)
                .setAuthenticationFailureHandler(authenticationFailureHandler);
    }
}
