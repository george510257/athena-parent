package com.athena.security.servlet.rest;

import com.athena.starter.web.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

/**
 * 手机号认证转换器
 *
 * @author george
 */
@Setter
public class MobileAuthenticationConverter implements AuthenticationConverter {
    /**
     * 手机号参数
     */
    private String mobileParameter = "mobile";

    /**
     * 转换
     *
     * @param request 请求
     * @return 认证
     */
    @Override
    public Authentication convert(HttpServletRequest request) {
        String mobile = obtainMobile(request);
        if (mobile == null) {
            return null;
        }
        return MobileAuthenticationToken.unauthenticated(mobile);
    }

    /**
     * 获取手机号
     *
     * @param request 请求
     * @return 手机号
     */
    private String obtainMobile(HttpServletRequest request) {
        return WebUtil.getParameter(request, mobileParameter);
    }
}
