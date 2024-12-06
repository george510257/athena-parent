package com.gls.athena.security.servlet.captcha;

import com.gls.athena.security.servlet.captcha.base.BaseCaptchaProvider;
import com.gls.athena.security.servlet.captcha.image.ImageCaptchaGenerator;
import com.gls.athena.security.servlet.captcha.image.ImageCaptchaProvider;
import com.gls.athena.security.servlet.captcha.image.ImageCaptchaSender;
import com.gls.athena.security.servlet.captcha.repository.ICaptchaRepository;
import com.gls.athena.security.servlet.captcha.repository.RedisCaptchaRepository;
import com.gls.athena.security.servlet.captcha.sms.SmsCaptchaGenerator;
import com.gls.athena.security.servlet.captcha.sms.SmsCaptchaProvider;
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
    private List<BaseCaptchaProvider<?>> providers = new ArrayList<>();
    /**
     * 验证码管理器
     */
    private Customizer<List<BaseCaptchaProvider<?>>> providersCustomizer = Customizer.withDefaults();
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
        CaptchaFilter captchaFilter = new CaptchaFilter();
        captchaFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // 创建默认的验证码提供器
        List<BaseCaptchaProvider<?>> providers = createDefaultProviders();
        if (!this.providers.isEmpty()) {
            providers.addAll(0, this.providers);
        }
        this.providersCustomizer.customize(providers);
        captchaFilter.setCaptchaManager(new CaptchaManager(providers));
        builder.addFilterBefore(postProcess(captchaFilter), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 创建默认的验证码提供器
     *
     * @return 验证码提供器列表
     */
    private List<BaseCaptchaProvider<?>> createDefaultProviders() {
        List<BaseCaptchaProvider<?>> providers = new ArrayList<>();
        providers.add(createImageCaptchaProvider());
        providers.add(createSmsCaptchaProvider());
        return providers;
    }

    /**
     * 创建短信验证码提供器
     *
     * @return 短信验证码提供器
     */
    private BaseCaptchaProvider<?> createSmsCaptchaProvider() {
        return new SmsCaptchaProvider()
                .setCodeParameterName(captchaProperties.getSms().getCodeParameterName())
                .setTargetParameterName(captchaProperties.getSms().getTargetParameterName())
                .setUrl(captchaProperties.getSms().getUrl())
                .setUrls(captchaProperties.getSms().getUrls())
                .setLoginProcessingUrl(captchaProperties.getSms().getLoginProcessingUrl())
                .setOauth2TokenUrl(captchaProperties.getSms().getOauth2TokenUrl())
                .setRepository(captchaRepository)
                .setGenerator(new SmsCaptchaGenerator()
                        .setLength(captchaProperties.getSms().getLength())
                        .setExpireIn(captchaProperties.getSms().getExpireIn()))
                .setSender(new SmsCaptchaSender()
                        .setTemplateCode(captchaProperties.getSms().getTemplateCode()));
    }

    /**
     * 创建图形验证码提供器
     *
     * @return 图形验证码提供器
     */
    private BaseCaptchaProvider<?> createImageCaptchaProvider() {
        return new ImageCaptchaProvider()
                .setCodeParameterName(captchaProperties.getImage().getCodeParameterName())
                .setTargetParameterName(captchaProperties.getImage().getTargetParameterName())
                .setUrl(captchaProperties.getImage().getUrl())
                .setUrls(captchaProperties.getImage().getUrls())
                .setLoginProcessingUrl(captchaProperties.getImage().getLoginProcessingUrl())
                .setOauth2TokenUrl(captchaProperties.getImage().getOauth2TokenUrl())
                .setUsernameParameter(captchaProperties.getImage().getUsernameParameter())
                .setRepository(captchaRepository)
                .setGenerator(new ImageCaptchaGenerator()
                        .setLength(captchaProperties.getImage().getLength())
                        .setExpireIn(captchaProperties.getImage().getExpireIn())
                        .setWidth(captchaProperties.getImage().getWidth())
                        .setHeight(captchaProperties.getImage().getHeight())
                        .setLineCount(captchaProperties.getImage().getLineCount())
                        .setFontSize(captchaProperties.getImage().getFontSize()))
                .setSender(new ImageCaptchaSender());
    }

}
