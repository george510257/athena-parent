package com.athena.security.servlet.authorization.customizer;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcUserInfoEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.stereotype.Component;

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
        OAuth2Authorization authentication = authenticationContext.getAuthorization();
        String username = authentication.getPrincipalName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            return null;
        }
        return new OidcUserInfo(BeanUtil.beanToMap(userDetails));
    }
}
