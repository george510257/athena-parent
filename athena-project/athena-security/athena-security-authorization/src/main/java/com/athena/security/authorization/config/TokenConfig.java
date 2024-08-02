package com.athena.security.authorization.config;

import com.athena.security.authorization.customizer.AccessTokenCustomizer;
import com.athena.security.authorization.customizer.JwtCustomizer;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.*;

import java.util.Optional;

/**
 * 令牌配置
 */
@Configuration
public class TokenConfig {
    /**
     * JWT自定义器
     */
    @Resource
    private Optional<JwtCustomizer> jwtCustomizer;
    /**
     * 访问令牌自定义器
     */
    @Resource
    private Optional<AccessTokenCustomizer> accessTokenCustomizer;

    /**
     * OAuth2令牌生成器
     *
     * @param jwtEncoder JWT编码器
     * @return OAuth2令牌生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public OAuth2TokenGenerator<? extends OAuth2Token> oauth2TokenGenerator(JwtEncoder jwtEncoder) {
        // JWT生成器
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtCustomizer.ifPresent(jwtGenerator::setJwtCustomizer);
        // 访问令牌生成器
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        accessTokenCustomizer.ifPresent(accessTokenGenerator::setAccessTokenCustomizer);
        // 刷新令牌生成器
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        // 委托OAuth2令牌生成器
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    /**
     * JWT编码器
     *
     * @param jwkSource JWK源
     * @return JWT编码器
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
