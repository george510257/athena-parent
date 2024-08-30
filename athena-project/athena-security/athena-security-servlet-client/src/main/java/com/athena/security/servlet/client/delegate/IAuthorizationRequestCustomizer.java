package com.athena.security.servlet.client.delegate;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * OAuth2 授权请求自定义器
 */
public interface IAuthorizationRequestCustomizer extends Consumer<OAuth2AuthorizationRequest.Builder>, Predicate<String> {
}
