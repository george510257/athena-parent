package com.athena.security.core.servlet.code.sms;

import cn.hutool.core.util.StrUtil;
import com.athena.security.core.servlet.code.VerificationCodeException;
import com.athena.security.core.servlet.code.VerificationCodeProperties;
import com.athena.security.core.servlet.code.base.VerificationCodeProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 短信验证码提供器
 */
@Component
public class SmsCodeProvider extends VerificationCodeProvider<SmsCode, SmsCodeGenerator, SmsCodeSender> {
    /**
     * 短信验证码配置
     */
    private final VerificationCodeProperties.Sms smsProperties;

    /**
     * 构造函数
     *
     * @param verificationCodeProperties 安全配置
     */
    public SmsCodeProvider(VerificationCodeProperties verificationCodeProperties) {
        this.smsProperties = verificationCodeProperties.getSms();
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
        String target = request.getRequest().getParameter(smsProperties.getTargetParameterName());
        if (StrUtil.isNotBlank(target)) {
            return target;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + smsProperties.getTargetParameterName());
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        String code = request.getRequest().getParameter(smsProperties.getCodeParameterName());
        if (StrUtil.isNotBlank(code)) {
            return code;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + smsProperties.getCodeParameterName());
    }

}
