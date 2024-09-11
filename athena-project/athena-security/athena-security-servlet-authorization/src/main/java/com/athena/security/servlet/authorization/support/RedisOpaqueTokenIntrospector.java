package com.athena.security.servlet.authorization.support;

import cn.hutool.core.bean.BeanUtil;
import com.athena.security.servlet.client.social.SocialUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;

/**
 * Redis非透明令牌解释器
 */
@Slf4j
@Component
public class RedisOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    /**
     * 授权服务
     */
    @Resource
    private OAuth2AuthorizationService authorizationService;
    /**
     * 用户详情服务
     */
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2Authorization oauth2Authorization = authorizationService.findByToken(token, null);
        if (oauth2Authorization == null) {
            // 无效令牌
            log.warn("Invalid token: {}", token);
            throw new InvalidBearerTokenException(token);
        }

        OAuth2Authorization.Token<OAuth2Token> authorizationToken = oauth2Authorization.getToken(token);
        if (authorizationToken == null || !authorizationToken.isActive()) {
            // 无效令牌
            log.warn("Invalid token: {}", token);
            throw new InvalidBearerTokenException(token);
        }
        // 返回令牌主体
        Authentication authentication = oauth2Authorization.getAttribute(Principal.class.getName());
        assert authentication != null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof SocialUser socialUser) {
            return convertToPrincipal(socialUser);
        }
        return new DefaultOAuth2AuthenticatedPrincipal(authentication.getName(),
                BeanUtil.beanToMap(authentication.getPrincipal()),
                new ArrayList<>(authentication.getAuthorities()));
    }

    private OAuth2AuthenticatedPrincipal convertToPrincipal(SocialUser socialUser) {
        String username = socialUser.getUsername();
        return new DefaultOAuth2AuthenticatedPrincipal(username,
                BeanUtil.beanToMap(userDetailsService.loadUserByUsername(username)),
                new ArrayList<>(socialUser.getAuthorities()));
    }

}
