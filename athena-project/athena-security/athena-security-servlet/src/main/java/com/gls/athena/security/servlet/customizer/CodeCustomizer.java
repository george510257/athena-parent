package com.gls.athena.security.servlet.customizer;

import com.gls.athena.security.core.properties.CoreSecurityProperties;
import com.gls.athena.security.servlet.code.CodeConfigurer;
import com.gls.athena.security.servlet.code.base.BaseCodeProvider;
import com.gls.athena.security.servlet.code.image.ImageCodeGenerator;
import com.gls.athena.security.servlet.code.image.ImageCodeProvider;
import com.gls.athena.security.servlet.code.repository.ICodeRepository;
import com.gls.athena.security.servlet.code.sms.SmsCodeGenerator;
import com.gls.athena.security.servlet.code.sms.SmsCodeProvider;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 验证码自定义器
 *
 * @author george
 */
@Component
public class CodeCustomizer implements Customizer<CodeConfigurer<HttpSecurity>> {
    /**
     * 核心安全属性配置
     */
    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    /**
     * 验证码仓库
     */
    @Resource
    private Optional<ICodeRepository> codeRepository;
    /**
     * 认证失败处理器
     */
    @Resource
    private Optional<AuthenticationFailureHandler> authenticationFailureHandler;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(CodeConfigurer<HttpSecurity> configurer) {
        // 提供者自定义器
        configurer.setProvidersCustomizer(this::providersCustomizer);
        // 验证码仓库
        codeRepository.ifPresent(configurer::setCodeRepository);
        // 认证失败处理器
        authenticationFailureHandler.ifPresent(configurer::setAuthenticationFailureHandler);
    }

    /**
     * 提供者自定义器
     *
     * @param providers 提供者列表
     */
    private void providersCustomizer(List<BaseCodeProvider<?>> providers) {
        CoreSecurityProperties.Rest rest = coreSecurityProperties.getRest();
        for (BaseCodeProvider<?> provider : providers) {
            if (provider instanceof ImageCodeProvider imageCodeProvider) {
                imageCodeProviderCustomizer(imageCodeProvider, rest);
            } else if (provider instanceof SmsCodeProvider smsCodeProvider) {
                smsCodeProviderCustomizer(smsCodeProvider, rest);
            }
        }
    }

    /**
     * 短信验证码提供者自定义
     *
     * @param smsCodeProvider 短信验证码提供者
     * @param rest            REST配置
     */
    private void smsCodeProviderCustomizer(SmsCodeProvider smsCodeProvider, CoreSecurityProperties.Rest rest) {
        CoreSecurityProperties.Sms sms = coreSecurityProperties.getCode().getSms();
        smsCodeProvider
                .setCodeParameterName(sms.getCodeParameterName())
                .setTargetParameterName(sms.getTargetParameterName())
                .setUrl(sms.getUrl())
                .setUrls(sms.getUrls())
                .setLoginProcessingUrl(rest.getLoginProcessingUrl())
                .setGenerator(new SmsCodeGenerator()
                        .setLength(sms.getLength())
                        .setExpireIn(sms.getExpireIn()));
    }

    /**
     * 图形验证码提供者自定义
     *
     * @param imageCodeProvider 图形验证码提供者
     * @param rest              REST配置
     */
    private void imageCodeProviderCustomizer(ImageCodeProvider imageCodeProvider, CoreSecurityProperties.Rest rest) {
        CoreSecurityProperties.Image image = coreSecurityProperties.getCode().getImage();
        imageCodeProvider
                .setCodeParameterName(image.getCodeParameterName())
                .setTargetParameterName(image.getTargetParameterName())
                .setUrl(image.getUrl())
                .setUrls(image.getUrls())
                .setLoginProcessingUrl(rest.getLoginProcessingUrl())
                .setUsernameParameter(rest.getUsernameParameter())
                .setGenerator(new ImageCodeGenerator()
                        .setWidth(image.getWidth())
                        .setHeight(image.getHeight())
                        .setLength(image.getLength())
                        .setExpireIn(image.getExpireIn())
                        .setFontSize(image.getFontSize())
                        .setLineCount(image.getLineCount()));
    }

}
