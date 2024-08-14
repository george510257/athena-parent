package com.athena.security.core.servlet.code.image;

import cn.hutool.core.util.StrUtil;
import com.athena.security.core.servlet.code.VerificationCodeException;
import com.athena.security.core.servlet.code.VerificationCodeProperties;
import com.athena.security.core.servlet.code.base.VerificationCodeProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 图片验证码提供器
 */
@Component
public class ImageCodeProvider extends VerificationCodeProvider<ImageCode, ImageCodeGenerator, ImageCodeSender> {
    /**
     * 图片验证码配置
     */
    private final VerificationCodeProperties.Image imageProperties;

    /**
     * 构造函数
     *
     * @param verificationCodeProperties 安全配置
     */
    public ImageCodeProvider(VerificationCodeProperties verificationCodeProperties) {
        this.imageProperties = verificationCodeProperties.getImage();
    }

    /**
     * 是否发送请求
     *
     * @param request 请求
     * @return 是否发送请求
     */
    @Override
    public boolean isSendRequest(ServletWebRequest request) {
        String url = imageProperties.getUrl();
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
        // 判断是否密码登录
        if (isPasswordLogin(request)) {
            return true;
        }
        List<String> urls = imageProperties.getUrls();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 是否密码登录
     *
     * @param request 请求
     * @return 是否密码登录
     */
    private boolean isPasswordLogin(ServletWebRequest request) {
        String requestURI = request.getRequest().getRequestURI();
        String grantType = request.getRequest().getParameter("grant_type");
        return StrUtil.containsIgnoreCase(requestURI, "/oauth2/token") && StrUtil.containsIgnoreCase(grantType, "password");
    }

    /**
     * 获取接收目标
     *
     * @param request 请求
     * @return 接收目标
     */
    @Override
    public String getTarget(ServletWebRequest request) {
        String target = request.getRequest().getParameter(imageProperties.getTargetParameterName());
        if (StrUtil.isNotBlank(target)) {
            return target;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + imageProperties.getTargetParameterName());
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        String code = request.getRequest().getParameter(imageProperties.getCodeParameterName());
        if (StrUtil.isNotBlank(code)) {
            return code;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + imageProperties.getCodeParameterName());
    }

}
