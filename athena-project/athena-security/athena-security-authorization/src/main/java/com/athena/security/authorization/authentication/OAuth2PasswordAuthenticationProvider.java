package com.athena.security.authorization.authentication;

import com.athena.security.authorization.support.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * OAuth2 密码认证提供者
 */
public class OAuth2PasswordAuthenticationProvider extends OAuth2BaseAuthenticationProvider {
    /**
     * 用户详情认证提供者
     */
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 构造函数
     *
     * @param authorizationService 授权服务
     * @param tokenGenerator       令牌生成器
     * @param userService          用户服务
     * @param passwordEncoder      密码编码器
     */
    public OAuth2PasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                UserService userService,
                                                PasswordEncoder passwordEncoder) {
        super(authorizationService, tokenGenerator);
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 获取用户名密码认证令牌
     *
     * @param baseAuthenticationToken 基础认证令牌
     * @return 用户名密码认证令牌
     */
    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(OAuth2BaseAuthenticationToken baseAuthenticationToken) {
        OAuth2PasswordAuthenticationToken token = (OAuth2PasswordAuthenticationToken) baseAuthenticationToken;
        UserDetails userDetails = userService.loadUserByUsername(token.getUsername());
        checkUser(userDetails, token.getPassword());
        return UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

    }

    private void checkUser(UserDetails userDetails, String password) {
        if (userDetails == null) {
            throw new BadCredentialsException("用户不存在");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
    }

    /**
     * 是否支持
     *
     * @param authentication 认证
     * @return 是否支持
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
