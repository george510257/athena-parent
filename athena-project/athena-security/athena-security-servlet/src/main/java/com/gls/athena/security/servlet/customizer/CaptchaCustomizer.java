package com.gls.athena.security.servlet.customizer;

import com.gls.athena.security.servlet.captcha.CaptchaConfigurer;
import com.gls.athena.security.servlet.captcha.CaptchaProperties;
import com.gls.athena.security.servlet.captcha.repository.ICaptchaRepository;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 验证码自定义器
 *
 * @author george
 */
@Component
public class CaptchaCustomizer implements Customizer<CaptchaConfigurer<HttpSecurity>> {

    /**
     * 核心安全配置属性
     */
    @Resource
    private CaptchaProperties captchaProperties;
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
        // 验证码配置
        configurer.setCaptchaProperties(captchaProperties);
        // 验证码仓库
        captchaRepository.ifPresent(configurer::setCaptchaRepository);
        // 认证失败处理器
        authenticationFailureHandler.ifPresent(configurer::setAuthenticationFailureHandler);
    }

}
