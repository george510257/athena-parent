package com.athena.security.servlet.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Setter
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationConverter authenticationConverter;

    public RestAuthenticationFilter() {
    }

    public RestAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authRequest = authenticationConverter.convert(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, Authentication authentication) {
        if (authentication instanceof AbstractAuthenticationToken abstractAuthenticationToken) {
            abstractAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        }
    }
}
