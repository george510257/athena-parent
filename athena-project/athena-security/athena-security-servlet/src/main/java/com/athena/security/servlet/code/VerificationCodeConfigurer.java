package com.athena.security.servlet.code;

import com.athena.security.servlet.code.base.BaseCodeProvider;
import com.athena.security.servlet.code.image.ImageCodeGenerator;
import com.athena.security.servlet.code.image.ImageCodeProvider;
import com.athena.security.servlet.code.image.ImageCodeSender;
import com.athena.security.servlet.code.repository.RedisVerificationCodeRepository;
import com.athena.security.servlet.code.repository.VerificationCodeRepository;
import com.athena.security.servlet.code.sms.SmsCodeGenerator;
import com.athena.security.servlet.code.sms.SmsCodeProvider;
import com.athena.security.servlet.code.sms.SmsCodeSender;
import com.athena.security.servlet.handler.DefaultAuthenticationFailureHandler;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 验证码配置
 */

@Setter
@Accessors(chain = true)
public final class VerificationCodeConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<VerificationCodeConfigurer<H>, H> {
    /**
     * 验证码存储器
     */
    private VerificationCodeRepository verificationCodeRepository = new RedisVerificationCodeRepository();
    /**
     * 认证失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler = new DefaultAuthenticationFailureHandler();
    /**
     * 验证码管理器
     */
    private List<BaseCodeProvider<?>> providers = new ArrayList<>();
    /**
     * 验证码管理器
     */
    private Consumer<List<BaseCodeProvider<?>>> providersConsumer = (providers) -> {
    };

    @Override
    public void configure(H builder) throws Exception {
        VerificationCodeFilter codeFilter = new VerificationCodeFilter();
        codeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // 创建默认的验证码提供器
        List<BaseCodeProvider<?>> providers = createDefaultProviders();
        if (!this.providers.isEmpty()) {
            providers.addAll(0, this.providers);
        }
        this.providersConsumer.accept(providers);
        codeFilter.setVerificationCodeManager(new VerificationCodeManager(providers));
        builder.addFilterBefore(postProcess(codeFilter), UsernamePasswordAuthenticationFilter.class);
    }

    private List<BaseCodeProvider<?>> createDefaultProviders() {
        List<BaseCodeProvider<?>> providers = new ArrayList<>();
        providers.add(new ImageCodeProvider()
                .setRepository(verificationCodeRepository)
                .setGenerator(new ImageCodeGenerator())
                .setSender(new ImageCodeSender()));
        providers.add(new SmsCodeProvider()
                .setRepository(verificationCodeRepository)
                .setGenerator(new SmsCodeGenerator())
                .setSender(new SmsCodeSender()));
        return providers;
    }

}
