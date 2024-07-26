package com.athena.security.resource.servlet.converter;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(UserDetailsService.class)
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // 获取用户名
        String username = jwt.getSubject();
        // 创建用户名密码认证令牌
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 返回认证令牌
        return UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
