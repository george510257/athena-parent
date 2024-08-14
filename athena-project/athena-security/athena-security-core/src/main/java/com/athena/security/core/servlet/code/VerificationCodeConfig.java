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
    @Resource
    private VerificationCodeProperties verificationCodeProperties;

    /**
     * 图片验证码生成器
     *
     * @return 图片验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public ImageCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setImage(verificationCodeProperties.getImage());
        return imageCodeGenerator;
    }

    /**
     * 短信验证码生成器
     *
     * @return 短信验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsCodeGenerator smsCodeGenerator() {
        SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
        smsCodeGenerator.setSms(verificationCodeProperties.getSms());
        return smsCodeGenerator;
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
     * 图片验证码提供器
     *
     * @param imageCodeGenerator         图片验证码生成器
     * @param imageCodeSender            图片验证码发送器
     * @param verificationCodeRepository 验证码存储器
     * @return 图片验证码提供器
     */
    @Bean
    @ConditionalOnMissingBean
    public ImageCodeProvider imageCodeProvider(ImageCodeGenerator imageCodeGenerator,
                                               ImageCodeSender imageCodeSender,
                                               VerificationCodeRepository verificationCodeRepository) {
        ImageCodeProvider imageCodeProvider = new ImageCodeProvider();
        imageCodeProvider.setGenerator(imageCodeGenerator);
        imageCodeProvider.setSender(imageCodeSender);
        imageCodeProvider.setRepository(verificationCodeRepository);
        imageCodeProvider.setImage(verificationCodeProperties.getImage());
        return imageCodeProvider;
    }

    /**
     * 短信验证码提供器
     *
     * @param smsCodeGenerator           短信验证码生成器
     * @param smsCodeSender              短信验证码发送器
     * @param verificationCodeRepository 验证码存储器
     * @return 短信验证码提供器
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsCodeProvider smsCodeProvider(SmsCodeGenerator smsCodeGenerator,
                                           SmsCodeSender smsCodeSender,
                                           VerificationCodeRepository verificationCodeRepository) {
        SmsCodeProvider smsCodeProvider = new SmsCodeProvider();
        smsCodeProvider.setGenerator(smsCodeGenerator);
        smsCodeProvider.setSender(smsCodeSender);
        smsCodeProvider.setRepository(verificationCodeRepository);
        smsCodeProvider.setSms(verificationCodeProperties.getSms());
        return smsCodeProvider;
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

    /**
     * 验证码过滤器
     *
     * @param verificationCodeManager 验证码管理器
     * @return 验证码过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeFilter verificationCodeFilter(VerificationCodeManager verificationCodeManager) {
        VerificationCodeFilter verificationCodeFilter = new VerificationCodeFilter();
        verificationCodeFilter.setVerificationCodeManager(verificationCodeManager);
        return verificationCodeFilter;
    }
}
