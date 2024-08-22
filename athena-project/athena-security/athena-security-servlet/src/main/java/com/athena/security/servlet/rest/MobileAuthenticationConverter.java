package com.athena.security.servlet.rest;

import com.athena.starter.web.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

@Setter
public class MobileAuthenticationConverter implements AuthenticationConverter {

    private String mobileParameter = "mobile";

    @Override
    public Authentication convert(HttpServletRequest request) {
        String mobile = obtainMobile(request);
        if (mobile == null) {
            return null;
        }
        return MobileAuthenticationToken.unauthenticated(mobile);
    }

    private String obtainMobile(HttpServletRequest request) {
        return WebUtil.getParameter(request, mobileParameter);
    }
}
