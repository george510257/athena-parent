package com.gls.athena.security.servlet.customizer;

import com.gls.athena.security.core.properties.CoreSecurityProperties;
import com.gls.athena.security.servlet.captcha.CaptchaConfigurer;
import com.gls.athena.security.servlet.captcha.base.BaseCaptchaProvider;
import com.gls.athena.security.servlet.captcha.image.ImageCaptchaGenerator;
import com.gls.athena.security.servlet.captcha.image.ImageCaptchaProvider;
import com.gls.athena.security.servlet.captcha.repository.ICaptchaRepository;
import com.gls.athena.security.servlet.captcha.sms.SmsCaptchaGenerator;
import com.gls.athena.security.servlet.captcha.sms.SmsCaptchaProvider;
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
public class CaptchaCustomizer implements Customizer<CaptchaConfigurer<HttpSecurity>> {
    /**
     * 核心安全属性配置
     */
    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    /**
     * 验证码仓库
     */
    @Resource
    private Optional<ICaptchaRepository> captchaRepository;
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
    public void customize(CaptchaConfigurer<HttpSecurity> configurer) {
        // 提供者自定义器
        configurer.setProvidersCustomizer(this::providersCustomizer);
        // 验证码仓库
        captchaRepository.ifPresent(configurer::setCaptchaRepository);
        // 认证失败处理器
        authenticationFailureHandler.ifPresent(configurer::setAuthenticationFailureHandler);
    }

    /**
     * 提供者自定义器
     *
     * @param providers 提供者列表
     */
    private void providersCustomizer(List<BaseCaptchaProvider<?>> providers) {
        CoreSecurityProperties.Rest rest = coreSecurityProperties.getRest();
        for (BaseCaptchaProvider<?> provider : providers) {
            if (provider instanceof ImageCaptchaProvider imageCaptchaProvider) {
                imageCaptchaProviderCustomizer(imageCaptchaProvider, rest);
            } else if (provider instanceof SmsCaptchaProvider smsCaptchaProvider) {
                smsCaptchaProviderCustomizer(smsCaptchaProvider, rest);
            }
        }
    }

    /**
     * 短信验证码提供者自定义
     *
     * @param smsCaptchaProvider 短信验证码提供者
     * @param rest               REST配置
     */
    private void smsCaptchaProviderCustomizer(SmsCaptchaProvider smsCaptchaProvider, CoreSecurityProperties.Rest rest) {
        CoreSecurityProperties.Sms sms = coreSecurityProperties.getCaptcha().getSms();
        smsCaptchaProvider
                .setCodeParameterName(sms.getCodeParameterName())
                .setTargetParameterName(sms.getTargetParameterName())
                .setUrl(sms.getUrl())
                .setUrls(sms.getUrls())
                .setLoginProcessingUrl(rest.getLoginProcessingUrl())
                .setGenerator(new SmsCaptchaGenerator()
                        .setLength(sms.getLength())
                        .setExpireIn(sms.getExpireIn()));
    }

    /**
     * 图形验证码提供者自定义
     *
     * @param imageCaptchaProvider 图形验证码提供者
     * @param rest                 REST配置
     */
    private void imageCaptchaProviderCustomizer(ImageCaptchaProvider imageCaptchaProvider, CoreSecurityProperties.Rest rest) {
        CoreSecurityProperties.Image image = coreSecurityProperties.getCaptcha().getImage();
        imageCaptchaProvider
                .setCodeParameterName(image.getCodeParameterName())
                .setTargetParameterName(image.getTargetParameterName())
                .setUrl(image.getUrl())
                .setUrls(image.getUrls())
                .setLoginProcessingUrl(rest.getLoginProcessingUrl())
                .setUsernameParameter(rest.getUsernameParameter())
                .setGenerator(new ImageCaptchaGenerator()
                        .setWidth(image.getWidth())
                        .setHeight(image.getHeight())
                        .setLength(image.getLength())
                        .setExpireIn(image.getExpireIn())
                        .setFontSize(image.getFontSize())
                        .setLineCount(image.getLineCount()));
    }

}
