package com.athena.security.servlet.code.sms;

import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.code.VerificationCodeException;
import com.athena.security.servlet.code.VerificationCodeProperties;
import com.athena.security.servlet.code.base.VerificationCodeProvider;
import com.athena.starter.web.util.WebUtil;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 短信验证码提供器
 */
@Setter
@Accessors(chain = true)
public class SmsCodeProvider extends VerificationCodeProvider<SmsCode> {
    /**
     * 短信验证码配置
     */
    private VerificationCodeProperties.Sms sms;

    /**
     * 是否发送请求
     *
     * @param request 请求
     * @return 是否发送请求
     */
    @Override
    public boolean isSendRequest(ServletWebRequest request) {
        String url = sms.getUrl();
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
        // 判断是否是短信登录
        if (isSmsLogin(request)) {
            return true;
        }
        List<String> urls = sms.getUrls();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 是否短信登录
     *
     * @param request 请求
     * @return 是否短信登录
     */
    private boolean isSmsLogin(ServletWebRequest request) {
        String requestURI = request.getRequest().getRequestURI();
        if (StrUtil.containsIgnoreCase(requestURI, "/api/mobileLogin")) {
            return true;
        }
        String grantType = request.getRequest().getParameter("grant_type");
        return StrUtil.containsIgnoreCase(requestURI, "/oauth2/token") && StrUtil.containsIgnoreCase(grantType, "sms");
    }

    /**
     * 获取目标
     *
     * @param request 请求
     * @return 目标
     */
    @Override
    public String getTarget(ServletWebRequest request) {
        String target = WebUtil.getParameter(request.getRequest(), sms.getTargetParameterName());
        if (StrUtil.isNotBlank(target)) {
            return target;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + sms.getTargetParameterName());
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        String code = WebUtil.getParameter(request.getRequest(), sms.getCodeParameterName());
        if (StrUtil.isNotBlank(code)) {
            return code;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + sms.getCodeParameterName());
    }

}
