package com.athena.security.core.common.code.sms;

import com.athena.security.core.common.SecurityProperties;
import com.athena.security.core.common.code.base.VerificationCodeGenerator;
import com.athena.security.core.common.code.base.VerificationCodeProvider;
import com.athena.security.core.common.code.base.VerificationCodeSender;
import com.athena.security.core.common.code.repository.VerificationCodeRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 短信验证码提供器
 */
@Component
public class SmsCodeProvider extends VerificationCodeProvider<SmsCode> {
    /**
     * 短信验证码配置
     */
    private final SecurityProperties.Sms smsProperties;

    /**
     * 构造函数
     *
     * @param verificationCodeRepository 验证码存储器
     * @param verificationCodeGenerator  验证码生成器
     * @param verificationCodeSender     验证码发送器
     * @param securityProperties         安全配置
     */
    public SmsCodeProvider(VerificationCodeRepository verificationCodeRepository,
                           VerificationCodeGenerator<SmsCode> verificationCodeGenerator,
                           VerificationCodeSender<SmsCode> verificationCodeSender,
                           SecurityProperties securityProperties) {
        super(verificationCodeRepository, verificationCodeGenerator, verificationCodeSender);
        this.smsProperties = securityProperties.getCode().getSms();
    }

    /**
     * 是否发送请求
     *
     * @param request 请求
     * @return 是否发送请求
     */
    @Override
    public boolean isSendRequest(ServletWebRequest request) {
        String url = smsProperties.getUrl();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match(url, request.getRequest().getRequestURI());
    }

    /**
     * 是否验证请求
     *
     * @param request 请求
     * @return 是否验证请求
     */
    @Override
    public boolean isVerifyRequest(ServletWebRequest request) {
        List<String> urls = smsProperties.getUrls();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 获取目标
     *
     * @param request 请求
     * @return 目标
     */
    @Override
    public String getTarget(ServletWebRequest request) {
        return request.getRequest().getParameter(smsProperties.getMobileParameterName());
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        return request.getRequest().getParameter(smsProperties.getParameterName());
    }

}
