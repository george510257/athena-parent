package com.athena.security.core.servlet.code;

import cn.hutool.json.JSONUtil;
import com.athena.common.bean.result.Result;
import com.athena.common.bean.result.ResultStatus;
import com.athena.security.core.servlet.code.base.VerificationCodeProvider;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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

    @Setter
    private AuthenticationFailureHandler authenticationFailureHandler = this::sendErrorResponse;
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

        // 无需校验验证码
        if (provider == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 发送验证码请求
        if (provider.isSendRequest(servletWebRequest)) {
            provider.send(servletWebRequest);
            return;
        }

        try {
            // 校验验证码请求
            provider.verify(servletWebRequest);
            // 继续执行过滤器链
            filterChain.doFilter(request, response);
        } catch (VerificationCodeException e) {
            log.error("验证码校验失败", e);
            // 校验失败处理
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        }
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

    /**
     * 发送错误响应
     *
     * @param request  请求
     * @param response 响应
     * @param e        异常
     */
    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        try {
            Result<String> result = ResultStatus.UNAUTHORIZED.toResult(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(result));
        } catch (IOException ioException) {
            log.error("验证码错误", ioException);
        }
    }
}
