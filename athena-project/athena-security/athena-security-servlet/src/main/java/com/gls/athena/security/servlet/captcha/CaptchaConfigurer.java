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
        providers.add(new ImageCaptchaProvider()
                .setRepository(captchaRepository)
                .setGenerator(new ImageCaptchaGenerator())
                .setSender(new ImageCaptchaSender()));
        providers.add(new SmsCaptchaProvider()
                .setRepository(captchaRepository)
                .setGenerator(new SmsCaptchaGenerator())
                .setSender(new SmsCaptchaSender()));
        return providers;
    }

}
