package com.athena.security.servlet.rest;

import com.athena.starter.web.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

/**
 * 用户名密码认证转换器
 *
 * @author george
 */
@Setter
public class UsernamePasswordAuthenticationConverter implements AuthenticationConverter {
    /**
     * 用户名参数
     */
    private String usernameParameter = "username";
    /**
     * 密码参数
     */
    private String passwordParameter = "password";

    /**
     * 转换
     *
     * @param request 请求
     * @return 认证
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        // 获取用户名
        String username = obtainUsername(request);
        // 获取密码
        String password = obtainPassword(request);
        // 如果用户名或密码为空，则返回null
        if (username == null || password == null) {
            return null;
        }
        // 返回认证信息
        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }

    /**
     * 获取用户名
     *
     * @param request 请求
     * @return 用户名
     */
    private String obtainUsername(HttpServletRequest request) {
        return WebUtil.getParameter(request, usernameParameter);
    }

    /**
     * 获取密码
     *
     * @param request 请求
     * @return 密码
     */
    private String obtainPassword(HttpServletRequest request) {
        return WebUtil.getParameter(request, passwordParameter);
    }

}
