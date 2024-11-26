package com.gls.athena.security.servlet.client.delegate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.function.Predicate;

/**
 * OAuth2 授权请求自定义器
 *
 * @author george
 */
public interface IAuthorizationRequestCustomizer extends Predicate<String> {
    /**
     * 接受请求自定义
     *
     * @param builder            授权请求构建器
     * @param request            请求
     * @param clientRegistration 客户端注册
     */
    void accept(OAuth2AuthorizationRequest.Builder builder, HttpServletRequest request, ClientRegistration clientRegistration);
}
