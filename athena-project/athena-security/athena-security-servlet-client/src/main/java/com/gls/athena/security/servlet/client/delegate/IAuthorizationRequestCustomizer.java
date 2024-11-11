package com.gls.athena.security.servlet.client.delegate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * OAuth2 授权请求自定义器
 *
 * @author george
 */
public interface IAuthorizationRequestCustomizer extends BiConsumer<OAuth2AuthorizationRequest.Builder, HttpServletRequest>, Predicate<String> {
}
