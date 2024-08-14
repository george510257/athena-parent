package com.athena.security.core.servlet.code;

import com.athena.security.core.servlet.code.base.VerificationCodeProvider;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 验证码过滤器
 */
@Slf4j
@Component
public class VerificationCodeFilter extends OncePerRequestFilter implements OrderedFilter {

    /**
     * 验证码管理器
     */
    @Resource
    private VerificationCodeManager verificationCodeManager;

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
        VerificationCodeProvider<?, ?, ?> provider = verificationCodeManager.getProvider(servletWebRequest);
        if (provider != null) {
            if (provider.isSendRequest(servletWebRequest)) {
                // 发送验证码请求
                provider.send(servletWebRequest);
                return;
            } else if (provider.isVerifyRequest(servletWebRequest)) {
                // 校验验证码请求
                try {
                    provider.verify(servletWebRequest);
                } catch (VerificationCodeException e) {
                    log.error("验证码校验失败", e);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                    return;
                }
            }
        }
        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

    /**
     * 获取过滤器顺序
     *
     * @return 过滤器顺序
     */
    @Override
    public int getOrder() {
        return REQUEST_WRAPPER_FILTER_MAX_ORDER - 9999;
    }
}
