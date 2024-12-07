package com.gls.athena.security.servlet.captcha;

import com.gls.athena.security.servlet.captcha.image.ImageCaptchaGenerator;
import com.gls.athena.security.servlet.captcha.image.ImageCaptchaSender;
import com.gls.athena.security.servlet.captcha.repository.ICaptchaRepository;
import com.gls.athena.security.servlet.captcha.repository.RedisCaptchaRepository;
import com.gls.athena.security.servlet.captcha.sms.SmsCaptchaGenerator;
import com.gls.athena.security.servlet.captcha.sms.SmsCaptchaSender;
import com.gls.athena.security.servlet.handler.DefaultAuthenticationFailureHandler;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证码配置
 *
 * @author george
 */
@Setter
@Accessors(chain = true)
public final class CaptchaConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<CaptchaConfigurer<H>, H> {
    /**
     * 验证码存储器
     */
    private ICaptchaRepository captchaRepository = new RedisCaptchaRepository();
    /**
     * 认证失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler = new DefaultAuthenticationFailureHandler();
    /**
     * 验证码管理器
     */
    private List<CaptchaProvider<?>> providers = new ArrayList<>();
    /**
     * 验证码管理器
     */
    private Customizer<List<CaptchaProvider<?>>> providersCustomizer = Customizer.withDefaults();
    /**
     * 验证码配置
     */
    private CaptchaProperties captchaProperties = new CaptchaProperties();

    /**
     * 配置
     *
     * @param builder 构建器
     */
    @Override
    public void configure(H builder) throws Exception {
        // 创建默认的验证码提供器
        List<CaptchaProvider<?>> providers = createDefaultProviders();
        if (!this.providers.isEmpty()) {
            providers.addAll(0, this.providers);
        }
        this.providersCustomizer.customize(providers);
        // 添加验证码过滤器
        CaptchaFilter captchaFilter = new CaptchaFilter(authenticationFailureHandler, new CaptchaProviderManager(providers));
        builder.addFilterBefore(postProcess(captchaFilter), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 创建默认的验证码提供器
     *
     * @return 验证码提供器列表
     */
    private List<CaptchaProvider<?>> createDefaultProviders() {
        List<CaptchaProvider<?>> providers = new ArrayList<>();
        providers.add(createImageCaptchaProvider());
        providers.add(createSmsCaptchaProvider());
        return providers;
    }

    /**
     * 创建短信验证码提供器
     *
     * @return 短信验证码提供器
     */
    private CaptchaProvider<?> createSmsCaptchaProvider() {
        CaptchaProperties.Sms sms = captchaProperties.getSms();
        SmsCaptchaGenerator smsCaptchaGenerator = new SmsCaptchaGenerator(sms.getLength(), sms.getExpireIn());
        SmsCaptchaSender smsCaptchaSender = new SmsCaptchaSender(sms.getTemplateCode());
        return new CaptchaProvider<>(captchaRepository, smsCaptchaGenerator, smsCaptchaSender,
                sms.getCodeParameterName(), sms.getTargetParameterName(), sms.getUrl(), sms.getUrls(), sms.getLoginProcessingUrl(), sms.getOauth2TokenUrl());
    }

    /**
     * 创建图形验证码提供器
     *
     * @return 图形验证码提供器
     */
    private CaptchaProvider<?> createImageCaptchaProvider() {
        CaptchaProperties.Image image = captchaProperties.getImage();
        ImageCaptchaGenerator imageCaptchaGenerator = new ImageCaptchaGenerator(image.getLength(), image.getExpireIn(), image.getWidth(), image.getHeight(), image.getLineCount(), image.getFontSize());
        ImageCaptchaSender imageCaptchaSender = new ImageCaptchaSender();
        return new CaptchaProvider<>(captchaRepository, imageCaptchaGenerator, imageCaptchaSender,
                image.getCodeParameterName(), image.getTargetParameterName(), image.getUrl(), image.getUrls(), image.getLoginProcessingUrl(), image.getOauth2TokenUrl());
    }

}
