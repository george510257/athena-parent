package com.athena.security.servlet.code.image;

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
 * 图片验证码提供器
 */
@Setter
@Accessors(chain = true)
public class ImageCodeProvider extends VerificationCodeProvider<ImageCode> {
    /**
     * 图片验证码配置
     */
    private VerificationCodeProperties.Image image;

    /**
     * 是否发送请求
     *
     * @param request 请求
     * @return 是否发送请求
     */
    @Override
    public boolean isSendRequest(ServletWebRequest request) {
        String url = image.getUrl();
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
        List<String> urls = image.getUrls();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 是否密码登录
     *
     * @param request 请求
     * @return 是否密码登录
     */
    protected boolean isPasswordLogin(ServletWebRequest request) {
        String requestURI = request.getRequest().getRequestURI();
        // 判断是否密码登录
        if (StrUtil.containsIgnoreCase(requestURI, "/api/restLogin")) {
            String username = WebUtil.getParameter(request.getRequest(), "username");
            return StrUtil.isNotBlank(username);
        }
        // 判断是否密码登录
        if (StrUtil.containsIgnoreCase(requestURI, "/oauth2/token")) {
            String grantType = WebUtil.getParameter(request.getRequest(), "grant_type");
            return StrUtil.containsIgnoreCase(grantType, "password");
        }
        return false;
    }

    /**
     * 获取接收目标
     *
     * @param request 请求
     * @return 接收目标
     */
    @Override
    public String getTarget(ServletWebRequest request) {
        String target = WebUtil.getParameter(request.getRequest(), image.getTargetParameterName());
        if (StrUtil.isNotBlank(target)) {
            return target;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + image.getTargetParameterName());
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        String code = WebUtil.getParameter(request.getRequest(), image.getCodeParameterName());
        if (StrUtil.isNotBlank(code)) {
            return code;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + image.getCodeParameterName());
    }

}
