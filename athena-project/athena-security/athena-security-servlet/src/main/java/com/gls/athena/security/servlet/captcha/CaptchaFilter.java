package com.gls.athena.security.servlet.captcha;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class CaptchaFilter extends OncePerRequestFilter {
    /**
     * 认证失败处理器
     */
    private final AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 验证码管理器
     */
    private final CaptchaProviderManager captchaProviderManager;

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
        CaptchaProvider<?> provider = captchaProviderManager.getProvider(servletWebRequest);
        // 无需校验验证码
        if (provider == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // 发送验证码请求
            if (provider.isSendRequest(servletWebRequest)) {
                provider.send(servletWebRequest);
                return;
            }
            // 校验验证码请求
            provider.verify(servletWebRequest);
            // 继续执行过滤器链
            filterChain.doFilter(request, response);
        } catch (CaptchaAuthenticationException e) {
            log.error("验证码校验失败", e);
            // 校验失败处理
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        }
    }

}
