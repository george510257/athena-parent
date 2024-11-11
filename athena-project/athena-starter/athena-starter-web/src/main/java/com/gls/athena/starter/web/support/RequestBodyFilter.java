package com.gls.athena.starter.web.support;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * RequestBodyFilter 用于解决流只能读取一次的问题
 *
 * @author george
 */
@Component
public class RequestBodyFilter extends OncePerRequestFilter implements OrderedFilter {

    /**
     * 执行过滤器
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException      IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 包装请求体
        filterChain.doFilter(new RequestBodyWrapper(request), response);
    }

    @Override
    public int getOrder() {
        return REQUEST_WRAPPER_FILTER_MAX_ORDER - 10000;
    }
}
