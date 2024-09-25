package com.athena.security.servlet.authorization.customizer;

import cn.hutool.core.bean.BeanUtil;
import com.athena.security.servlet.client.social.SocialUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;

/**
 * 资源服务器自定义
 *
 * @author george
 */
@Slf4j
@Component
public class OAuth2ResourceServerCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

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

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        // 默认配置
        configurer.opaqueToken(this::opaqueToken);
    }

    /**
     * 不透明令牌
     *
     * @param configurer 配置器
     */
    private void opaqueToken(OAuth2ResourceServerConfigurer<HttpSecurity>.OpaqueTokenConfigurer configurer) {
        // 令牌解释器
        configurer.introspector(this::introspector);
    }

    /**
     * 令牌解释器
     *
     * @param token 令牌
     * @return 令牌主体
     */
    private OAuth2AuthenticatedPrincipal introspector(String token) {
        // 查询授权
        OAuth2Authorization oauth2Authorization = authorizationService.findByToken(token, null);
        if (oauth2Authorization == null) {
            // 无效令牌
            log.warn("Invalid token: {}", token);
            throw new InvalidBearerTokenException(token);
        }
        // 查询令牌
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

    /**
     * 转换为主体
     *
     * @param socialUser 社交用户
     * @return 主体
     */
    private OAuth2AuthenticatedPrincipal convertToPrincipal(SocialUser socialUser) {
        String username = socialUser.getUsername();
        return new DefaultOAuth2AuthenticatedPrincipal(username,
                BeanUtil.beanToMap(userDetailsService.loadUserByUsername(username)),
                new ArrayList<>(socialUser.getAuthorities()));
    }

}
