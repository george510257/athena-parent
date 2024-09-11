package com.athena.security.servlet.authorization.customizer;

import cn.hutool.core.bean.BeanUtil;
import com.athena.security.servlet.client.social.SocialUser;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcUserInfoEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * 用户信息端点自定义器
 *
 * @author george
 */
@Component
public class OidcUserInfoEndpointCustomizer implements Customizer<OidcUserInfoEndpointConfigurer> {
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcUserInfoEndpointConfigurer configurer) {
        configurer.userInfoMapper(this::getUserInfo);
    }

    /**
     * 获取用户信息
     *
     * @param authenticationContext 上下文
     * @return 用户信息
     */
    private OidcUserInfo getUserInfo(OidcUserInfoAuthenticationContext authenticationContext) {
        OAuth2Authorization oauth2Authorization = authenticationContext.getAuthorization();
        Authentication authentication = oauth2Authorization.getAttribute(Principal.class.getName());
        assert authentication != null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof SocialUser socialUser) {
            String username = socialUser.getUsername();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new OidcUserInfo(BeanUtil.beanToMap(userDetails));
        }
        return new OidcUserInfo(BeanUtil.beanToMap(principal));
    }
}
