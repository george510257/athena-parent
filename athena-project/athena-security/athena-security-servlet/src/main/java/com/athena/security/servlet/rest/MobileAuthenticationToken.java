package com.athena.security.servlet.rest;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public MobileAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    public static MobileAuthenticationToken unauthenticated(Object principal) {
        return new MobileAuthenticationToken(principal);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
