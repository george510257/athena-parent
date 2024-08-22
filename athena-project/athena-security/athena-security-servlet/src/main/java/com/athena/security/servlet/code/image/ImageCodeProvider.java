package com.athena.security.servlet.code.image;

import cn.hutool.core.util.StrUtil;
import com.athena.security.core.properties.CoreSecurityProperties;
import com.athena.security.servlet.code.VerificationCodeException;
import com.athena.security.servlet.code.base.BaseCodeProvider;
import com.athena.starter.web.util.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * 图片验证码提供器
 */
@RequiredArgsConstructor
public class ImageCodeProvider extends BaseCodeProvider<ImageCode> {
    /**
     * 图片验证码配置
     */
    private final CoreSecurityProperties properties;

    /**
     * 是否发送请求
     *
     * @param request 请求
     * @return 是否发送请求
     */
    @Override
    public boolean isSendRequest(ServletWebRequest request) {
        CoreSecurityProperties.Image image = properties.getVerificationCode().getImage();
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
        CoreSecurityProperties.Image image = properties.getVerificationCode().getImage();
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
        CoreSecurityProperties.Rest rest = properties.getRest();
        String requestURI = request.getRequest().getRequestURI();
        // 判断是否密码登录
        if (StrUtil.containsIgnoreCase(requestURI, rest.getLoginProcessingUrl())) {
            String username = WebUtil.getParameter(request.getRequest(), rest.getUsernameParameter());
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
        CoreSecurityProperties.Image image = properties.getVerificationCode().getImage();
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
        CoreSecurityProperties.Image image = properties.getVerificationCode().getImage();
        String code = WebUtil.getParameter(request.getRequest(), image.getCodeParameterName());
        if (StrUtil.isNotBlank(code)) {
            return code;
        }
        throw new VerificationCodeException("参数不能为空 parameterName: " + image.getCodeParameterName());
    }

}
