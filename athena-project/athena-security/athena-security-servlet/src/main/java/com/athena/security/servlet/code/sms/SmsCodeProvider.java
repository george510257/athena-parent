package com.athena.security.servlet.code.sms;

import cn.hutool.core.util.StrUtil;
import com.athena.security.servlet.code.VerificationCodeException;
import com.athena.security.servlet.code.base.BaseCodeProvider;
import com.athena.starter.web.util.WebUtil;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信验证码提供器
 *
 * @author george
 */
@Setter
@Accessors(chain = true)
public class SmsCodeProvider extends BaseCodeProvider<SmsCode> {
    /**
     * 验证码参数名
     */
    private String codeParameterName = "smsCode";
    /**
     * 手机号参数名
     */
    private String targetParameterName = "mobile";
    /**
     * 获取短信验证码url
     */
    private String url = "/code/sms";
    /**
     * 需要校验验证码的url
     */
    private List<String> urls = new ArrayList<>();
    /**
     * 登录处理 URL
     */
    private String loginProcessingUrl = "/login";
    /**
     * oauth2 token url
     */
    private String oauth2TokenUrl = "/oauth2/token";

    /**
     * 是否发送请求
     *
     * @param request 请求
     * @return 是否发送请求
     */
    @Override
    public boolean isSendRequest(ServletWebRequest request) {
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
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 是否短信登录
     *
     * @param request 请求
     * @return 是否短信登录
     */
    protected boolean isSmsLogin(ServletWebRequest request) {
        // 判断是否是短信登录
        String requestURI = request.getRequest().getRequestURI();
        if (StrUtil.containsIgnoreCase(requestURI, loginProcessingUrl)) {
            String mobile = WebUtil.getParameter(request.getRequest(), targetParameterName);
            return StrUtil.isNotBlank(mobile);
        }
        // 判断是否是短信登录
        if (StrUtil.containsIgnoreCase(requestURI, oauth2TokenUrl)) {
            String grantType = WebUtil.getParameter(request.getRequest(), OAuth2ParameterNames.GRANT_TYPE);
            return StrUtil.containsIgnoreCase(grantType, "sms");
        }
        return false;
    }

    /**
     * 获取目标
     *
     * @param request 请求
     * @return 目标
     */
    @Override
    public String getTarget(ServletWebRequest request) {
        String target = WebUtil.getParameter(request.getRequest(), targetParameterName);
        if (StrUtil.isNotBlank(target)) {
            return target;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + targetParameterName);
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        String code = WebUtil.getParameter(request.getRequest(), codeParameterName);
        if (StrUtil.isNotBlank(code)) {
            return code;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + codeParameterName);
    }

}
