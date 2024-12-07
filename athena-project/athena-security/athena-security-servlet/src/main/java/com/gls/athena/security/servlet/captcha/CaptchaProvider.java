package com.gls.athena.security.servlet.captcha;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import com.gls.athena.security.servlet.captcha.base.BaseCaptcha;
import com.gls.athena.security.servlet.captcha.base.ICaptchaGenerator;
import com.gls.athena.security.servlet.captcha.base.ICaptchaSender;
import com.gls.athena.security.servlet.captcha.repository.ICaptchaRepository;
import com.gls.athena.starter.web.util.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 验证码提供器
 *
 * @param <Captcha> 验证码类型
 * @author george
 */
@RequiredArgsConstructor
public class CaptchaProvider<Captcha extends BaseCaptcha> {
    /**
     * 验证码存储器
     */
    private final ICaptchaRepository repository;
    /**
     * 验证码生成器
     */
    private final ICaptchaGenerator<Captcha> generator;
    /**
     * 验证码发送器
     */
    private final ICaptchaSender<Captcha> sender;
    /**
     * code参数名
     */
    private final String codeParameterName;
    /**
     * target参数名
     */
    private final String targetParameterName;
    /**
     * 发送验证码url
     */
    private final String url;
    /**
     * 需要校验验证码的url
     */
    private final List<String> urls;
    /**
     * 登录处理 URL
     */
    private final String loginProcessingUrl;
    /**
     * oauth2 token url
     */
    private final String oauth2TokenUrl;

    /**
     * 发送验证码
     *
     * @param request 请求
     */
    public void send(ServletWebRequest request) {
        // 获取接收目标
        String target = getTarget(request);
        // 生成验证码
        Captcha captcha = generator.generate();
        // 保存验证码
        repository.save(target, captcha);
        // 发送验证码
        sender.send(target, captcha, request.getResponse());
    }

    /**
     * 验证验证码
     *
     * @param request 请求
     */
    public void verify(ServletWebRequest request) {
        // 获取接收目标和验证码
        String target = getTarget(request);
        String code = getCode(request);
        // 获取验证码
        BaseCaptcha baseCaptcha = repository.get(target);
        // 验证码不存在或已过期
        if (baseCaptcha == null) {
            throw new CaptchaAuthenticationException("验证码不存在或已过期");
        }
        // 验证码正确且未过期
        if (baseCaptcha.getCode().equals(code)
                && baseCaptcha.getExpireTime().getTime() > System.currentTimeMillis()) {
            repository.remove(target);
        } else {
            // 验证码错误
            throw new CaptchaAuthenticationException("验证码错误");
        }
    }

    /**
     * 是否支持
     *
     * @param request 请求
     * @return 是否支持
     */
    public boolean support(ServletWebRequest request) {
        if (isSendRequest(request)) {
            return true;
        }
        return isVerifyRequest(request);
    }

    /**
     * 是否是发送请求
     *
     * @param request 请求
     * @return 是否是发送请求
     */
    public boolean isSendRequest(ServletWebRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match(url, request.getRequest().getRequestURI());
    }

    /**
     * 是否是校验请求
     *
     * @param request 请求
     * @return 是否是校验请求
     */
    private boolean isVerifyRequest(ServletWebRequest request) {
        if (isLogin(request)) {
            return true;
        }
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 是否登录
     *
     * @param request 请求
     * @return 是否登录
     */
    private boolean isLogin(ServletWebRequest request) {
        String requestURI = request.getRequest().getRequestURI();
        if (requestURI.contains(loginProcessingUrl)) {
            String target = WebUtil.getParameter(request.getRequest(), targetParameterName);
            return StrUtil.isNotBlank(target);
        }
        if (requestURI.contains(oauth2TokenUrl)) {
            String grantType = request.getParameter("grant_type");
            return StrUtil.containsIgnoreCase(grantType, "password") || StrUtil.containsIgnoreCase(grantType, "sms");
        }
        return false;
    }

    /**
     * 获取接收目标
     *
     * @param request 请求
     * @return 接收目标
     */
    private String getTarget(ServletWebRequest request) {
        return WebUtil.getParameter(request.getRequest(), targetParameterName);
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    private String getCode(ServletWebRequest request) {
        return WebUtil.getParameter(request.getRequest(), codeParameterName);
    }

}
