package com.athena.security.core.common.code;

import com.athena.security.core.common.code.base.VerificationCodeProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 验证码过滤器
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    /**
     * 验证码管理器
     */
    private final VerificationCodeManager verificationCodeManager;

    /**
     * 构造函数
     *
     * @param verificationCodeManager 验证码管理器
     */
    public VerificationCodeFilter(VerificationCodeManager verificationCodeManager) {
        this.verificationCodeManager = verificationCodeManager;
    }

    /**
     * 过滤器逻辑
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException 异常
     * @throws IOException      异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        VerificationCodeProvider provider = verificationCodeManager.getProvider(servletWebRequest);
        if (provider != null) {
            if (provider.isSendRequest(servletWebRequest)) {
                // 发送验证码请求
                provider.send(servletWebRequest);
                return;
            } else if (provider.isVerifyRequest(servletWebRequest)) {
                // 校验验证码请求
                provider.verify(servletWebRequest);
            }
        }
        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

}
