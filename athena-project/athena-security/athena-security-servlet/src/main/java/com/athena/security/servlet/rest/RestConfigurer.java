package com.athena.security.servlet.rest;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class RestConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, RestConfigurer<H>, RestAuthenticationFilter> {

    private final List<AuthenticationConverter> authenticationConverters = new ArrayList<>();
    private final List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
    private Consumer<List<AuthenticationConverter>> authenticationConvertersConsumer = (authenticationConverters) -> {
    };
    private Consumer<List<AuthenticationProvider>> authenticationProvidersConsumer = (authenticationProviders) -> {
    };

    public RestConfigurer() {
        super(new RestAuthenticationFilter(), null);
    }

    public RestConfigurer<H> loginPage(String loginPage) {
        return super.loginPage(loginPage);
    }

    public RestConfigurer<H> authenticationConverter(AuthenticationConverter authenticationConverter) {
        this.authenticationConverters.add(authenticationConverter);
        return this;
    }

    public RestConfigurer<H> authenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProviders.add(authenticationProvider);
        return this;
    }

    public RestConfigurer<H> authenticationConverters(Consumer<List<AuthenticationConverter>> authenticationConvertersConsumer) {
        this.authenticationConvertersConsumer = authenticationConvertersConsumer;
        return this;
    }

    public RestConfigurer<H> authenticationProviders(Consumer<List<AuthenticationProvider>> authenticationProvidersConsumer) {
        this.authenticationProvidersConsumer = authenticationProvidersConsumer;
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    @Override
    public void configure(H http) throws Exception {
        // 设置默认的认证转换器
        List<AuthenticationConverter> authenticationConverters = createDefaultAuthenticationConverters();
        if (!this.authenticationConverters.isEmpty()) {
            authenticationConverters.addAll(0, this.authenticationConverters);
        }
        this.authenticationConvertersConsumer.accept(authenticationConverters);
        getAuthenticationFilter().setAuthenticationConverter(new DelegatingAuthenticationConverter(authenticationConverters));
        // 设置默认的认证提供者
        List<AuthenticationProvider> authenticationProviders = createDefaultAuthenticationProviders();
        if (!this.authenticationProviders.isEmpty()) {
            authenticationProviders.addAll(this.authenticationProviders);
        }
        this.authenticationProvidersConsumer.accept(authenticationProviders);
        authenticationProviders.forEach(
                (authenticationProvider) -> http.authenticationProvider(postProcess(authenticationProvider)));
        // 配置过滤器
        super.configure(http);
    }

    private List<AuthenticationConverter> createDefaultAuthenticationConverters() {
        List<AuthenticationConverter> authenticationConverters = new ArrayList<>();
        authenticationConverters.add(new MobileAuthenticationConverter());
        authenticationConverters.add(new UsernamePasswordAuthenticationConverter());
        return authenticationConverters;
    }

    private List<AuthenticationProvider> createDefaultAuthenticationProviders() {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        UserDetailsService userDetailsService = SpringUtil.getBean(UserDetailsService.class);
        if (userDetailsService != null) {
            authenticationProviders.add(new MobileAuthenticationProvider(userDetailsService));
        }
        return authenticationProviders;
    }
}
