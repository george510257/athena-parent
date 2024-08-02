package com.athena.security.authorization.customizer;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcUserInfoEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.stereotype.Component;

/**
 * 用户信息端点自定义器
 */
@Component
public class UserInfoEndpointCustomizer implements Customizer<OidcUserInfoEndpointConfigurer> {
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 自定义
     *
     * @param configurer 配置器
     */
    @Override
    public void customize(OidcUserInfoEndpointConfigurer configurer) {
        configurer.userInfoMapper(this::userInfo);
    }

    private OidcUserInfo userInfo(OidcUserInfoAuthenticationContext context) {
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        return new OidcUserInfo(BeanUtil.beanToMap(principal));
    }
}
