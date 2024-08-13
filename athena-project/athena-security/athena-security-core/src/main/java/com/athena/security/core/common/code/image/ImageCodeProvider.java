package com.athena.security.core.common.code.image;

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
 * 图片验证码提供器
 */
@Component
public class ImageCodeProvider extends VerificationCodeProvider<ImageCode> {
    /**
     * 图片验证码配置
     */
    private final SecurityProperties.Image imageProperties;

    /**
     * 构造函数
     *
     * @param verificationCodeRepository 验证码存储器
     * @param verificationCodeGenerator  验证码生成器
     * @param verificationCodeSender     验证码发送器
     * @param securityProperties         安全配置
     */
    public ImageCodeProvider(VerificationCodeRepository verificationCodeRepository,
                             VerificationCodeGenerator<ImageCode> verificationCodeGenerator,
                             VerificationCodeSender<ImageCode> verificationCodeSender,
                             SecurityProperties securityProperties) {
        super(verificationCodeRepository, verificationCodeGenerator, verificationCodeSender);
        this.imageProperties = securityProperties.getCode().getImage();
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
        List<String> urls = imageProperties.getUrls();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return urls.stream().anyMatch(url -> pathMatcher.match(url, request.getRequest().getRequestURI()));
    }

    /**
     * 获取接收目标
     *
     * @param request 请求
     * @return 接收目标
     */
    @Override
    public String getTarget(ServletWebRequest request) {
        return request.getRequest().getParameter(imageProperties.getKeyParameterName());
    }

    /**
     * 获取验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @Override
    public String getCode(ServletWebRequest request) {
        return request.getRequest().getParameter(imageProperties.getParameterName());
    }

}
