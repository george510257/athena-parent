package com.athena.security.authorization.config;

import com.athena.security.authorization.customizer.OAuth2AuthorizationServerCustomizer;
import com.athena.security.authorization.support.RedisOAuth2AuthorizationConsentService;
import com.athena.security.authorization.support.RedisOAuth2AuthorizationService;
import com.athena.security.core.servlet.customizer.AuthorizeHttpRequestsCustomizer;
import com.athena.security.core.servlet.customizer.CsrfCustomizer;
import com.athena.security.core.servlet.customizer.ExceptionHandlingCustomizer;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

/**
 * 授权配置
 */
@AutoConfiguration
public class AuthorizationConfig {

    /**
     * 授权安全过滤器链
     *
     * @param http                            Http安全
     * @param authorizationServerCustomizer   授权服务器自定义器
     * @param authorizeHttpRequestsCustomizer 请求授权自定义器
     * @param exceptionHandlingCustomizer     异常处理自定义器
     * @param csrfCustomizer                  CSRF自定义器
     * @return 授权安全过滤器链
     */
    @Bean
    @SneakyThrows
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnBean({HttpSecurity.class, OAuth2AuthorizationServerCustomizer.class})
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http,
                                                                OAuth2AuthorizationServerCustomizer authorizationServerCustomizer,
                                                                AuthorizeHttpRequestsCustomizer authorizeHttpRequestsCustomizer,
                                                                ExceptionHandlingCustomizer exceptionHandlingCustomizer,
                                                                CsrfCustomizer csrfCustomizer) {
        // OAuth2认证服务器配置器
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, authorizationServerCustomizer);
        // 自定义授权请求
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer);
        // 自定义异常处理
        http.exceptionHandling(exceptionHandlingCustomizer);
        // 自定义CSRF
        http.csrf(csrfCustomizer);
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizationService authorizationService() {
        return new RedisOAuth2AuthorizationService();
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new RedisOAuth2AuthorizationConsentService();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password("{noop}admin")
                .authorities("ROLE_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient messagingClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .redirectUri("http://localhost:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://localhost:8080/authorized")
                .redirectUri("https://www.baidu.com")
                .postLogoutRedirectUri("http://localhost:8080/logged-out")
                .scope("openid")
                .scope("profile")
                .scope("message.read")
                .scope("message.write")
                .scope("user.read")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        return new InMemoryRegisteredClientRepository(messagingClient);
    }
}
