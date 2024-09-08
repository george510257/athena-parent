package com.athena.security.servlet.client.delegate;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.function.Predicate;

/**
 * OAuth2AccessTokenResponseClient 定制器
 *
 * @author george
 */
public interface IAuthorizationCodeTokenResponseClientCustomizer
        extends Customizer<DefaultAuthorizationCodeTokenResponseClient>, Predicate<String> {

    /**
     * 自定义响应
     *
     * @param request  授权码授权请求
     * @param response 响应
     * @return 响应
     */
    default OAuth2AccessTokenResponse customResponse(OAuth2AuthorizationCodeGrantRequest request, OAuth2AccessTokenResponse response) {
        // 默认不做任何处理
        return response;
    }
}
