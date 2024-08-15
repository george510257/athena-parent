package com.athena.security.authorization.authentication;

import com.athena.security.authorization.support.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * OAuth2 短信认证提供者
 */
@Slf4j
public class OAuth2SmsAuthenticationProvider extends OAuth2BaseAuthenticationProvider {
    /**
     * 用户服务
     */
    private final IUserService userService;

    /**
     * 构造函数
     *
     * @param authorizationService 认证服务
     * @param tokenGenerator       令牌生成器
     * @param userService          用户服务
     */
    public OAuth2SmsAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                           OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                           IUserService userService) {
        super(authorizationService, tokenGenerator);
        this.userService = userService;
    }

    /**
     * 获取用户名密码认证令牌
     *
     * @param baseAuthenticationToken 基础认证令牌
     * @return 用户名密码认证令牌
     */
    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(OAuth2BaseAuthenticationToken baseAuthenticationToken) {
        OAuth2SmsAuthenticationToken token = (OAuth2SmsAuthenticationToken) baseAuthenticationToken;
        UserDetails userDetails = userService.loadUserByMobile(token.getMobile());
        checkUser(userDetails);
        return UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    private void checkUser(UserDetails userDetails) {
        if (userDetails == null) {
            throw new BadCredentialsException("用户不存在");
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
        return OAuth2SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
