package com.athena.security.servlet.customizer;

import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.code.VerificationCodeConfigurer;
import com.athena.security.servlet.code.base.BaseCodeProvider;
import com.athena.security.servlet.code.image.ImageCodeGenerator;
import com.athena.security.servlet.code.image.ImageCodeProvider;
import com.athena.security.servlet.code.repository.VerificationCodeRepository;
import com.athena.security.servlet.code.sms.SmsCodeGenerator;
import com.athena.security.servlet.code.sms.SmsCodeProvider;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VerificationCodeCustomizer implements Customizer<VerificationCodeConfigurer<HttpSecurity>> {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Resource
    private Optional<VerificationCodeRepository> verificationCodeRepository;

    @Resource
    private Optional<AuthenticationFailureHandler> authenticationFailureHandler;

    @Override
    public void customize(VerificationCodeConfigurer<HttpSecurity> configurer) {
        configurer.setProvidersConsumer(this::providersConsumer);
        verificationCodeRepository.ifPresent(configurer::setVerificationCodeRepository);
        authenticationFailureHandler.ifPresent(configurer::setAuthenticationFailureHandler);
    }

    private void providersConsumer(List<BaseCodeProvider<?>> providers) {
        CoreSecurityProperties.Rest rest = coreSecurityProperties.getRest();
        for (BaseCodeProvider<?> provider : providers) {
            if (provider instanceof ImageCodeProvider imageCodeProvider) {
                CoreSecurityProperties.Image image = coreSecurityProperties.getVerificationCode().getImage();
                imageCodeProvider
                        .setCodeParameterName(image.getCodeParameterName())
                        .setTargetParameterName(image.getTargetParameterName())
                        .setUrl(image.getUrl())
                        .setUrls(image.getUrls())
                        .setLoginProcessingUrl(rest.getLoginProcessingUrl())
                        .setUsernameParameter(rest.getUsernameParameter());
                ImageCodeGenerator imageCodeGenerator = (ImageCodeGenerator) imageCodeProvider.getGenerator();
                imageCodeGenerator
                        .setWidth(image.getWidth())
                        .setHeight(image.getHeight())
                        .setLength(image.getLength())
                        .setExpireIn(image.getExpireIn())
                        .setFontSize(image.getFontSize())
                        .setLineCount(image.getLineCount());
            } else if (provider instanceof SmsCodeProvider smsCodeProvider) {
                CoreSecurityProperties.Sms sms = coreSecurityProperties.getVerificationCode().getSms();
                smsCodeProvider
                        .setCodeParameterName(sms.getCodeParameterName())
                        .setTargetParameterName(sms.getTargetParameterName())
                        .setUrl(sms.getUrl())
                        .setUrls(sms.getUrls())
                        .setLoginProcessingUrl(rest.getLoginProcessingUrl());
                SmsCodeGenerator smsCodeGenerator = (SmsCodeGenerator) smsCodeProvider.getGenerator();
                smsCodeGenerator
                        .setLength(sms.getLength())
                        .setExpireIn(sms.getExpireIn());
            }
        }

    }
}
