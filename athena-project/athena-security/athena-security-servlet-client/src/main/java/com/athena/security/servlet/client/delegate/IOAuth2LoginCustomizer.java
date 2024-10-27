package com.athena.security.servlet.client.delegate;

/**
 * OAuth2 登录自定义器
 *
 * @author george
 * @see IAuthorizationCodeTokenResponseClientAdapter
 * @see IAuthorizationRequestCustomizer
 * @see IOAuth2UserServiceAdapter
 */
public interface IOAuth2LoginCustomizer extends IAuthorizationCodeTokenResponseClientAdapter, IAuthorizationRequestCustomizer, IOAuth2UserServiceAdapter {
}
