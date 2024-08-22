package com.athena.security.servlet.rest;

import com.athena.starter.web.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

@Setter
public class UsernamePasswordAuthenticationConverter implements AuthenticationConverter {

    private String usernameParameter = "username";
    private String passwordParameter = "password";

    @Override
    public Authentication convert(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (username == null || password == null) {
            return null;
        }
        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }

    private String obtainUsername(HttpServletRequest request) {
        return WebUtil.getParameter(request, usernameParameter);
    }

    private String obtainPassword(HttpServletRequest request) {
        return WebUtil.getParameter(request, passwordParameter);
    }

}
