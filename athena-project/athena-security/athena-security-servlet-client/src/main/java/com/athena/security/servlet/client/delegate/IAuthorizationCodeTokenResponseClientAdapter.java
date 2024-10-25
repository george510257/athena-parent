package com.athena.security.servlet.client.delegate;

import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;

import java.util.function.Predicate;

/**
 * 授权码令牌响应客户端适配器
 *
 * @author george
 */
public interface IAuthorizationCodeTokenResponseClientAdapter
        extends OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>, Predicate<String> {

}
