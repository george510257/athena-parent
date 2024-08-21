package com.athena.security.servlet.mobile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Slf4j
public final class MobileConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, MobileConfigurer<H>, MobileAuthenticationFilter> {

    private AuthenticationProvider authenticationProvider;

    public MobileConfigurer() {
        super(new MobileAuthenticationFilter(), null);
        mobileParameter("mobile");
    }

    public MobileConfigurer<H> loginPage(String loginPage) {
        return super.loginPage(loginPage);
    }

    public MobileConfigurer<H> mobileParameter(String mobileParameter) {
        getAuthenticationFilter().setMobileParameter(mobileParameter);
        return this;
    }

    public MobileConfigurer<H> authenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        return this;
    }

    public MobileConfigurer<H> failureForwardUrl(String forwardUrl) {
        failureHandler(new ForwardAuthenticationFailureHandler(forwardUrl));
        return this;
    }

    public MobileConfigurer<H> successForwardUrl(String forwardUrl) {
        successHandler(new ForwardAuthenticationSuccessHandler(forwardUrl));
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    @Override
    public void configure(H http) throws Exception {
        try {
            super.configure(http);
        } catch (Exception e) {
            log.info("MobileConfigurer configure error:{}", e.getMessage());
        }
        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);
    }
}
