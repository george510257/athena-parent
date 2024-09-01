package com.athena.security.servlet.client.delegate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;

import java.util.function.Predicate;

/**
 * OAuth2 授权码请求实体转换器
 *
 * @author george
 */
public interface IAuthorizationCodeGrantRequestConverter extends Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>>, Predicate<String> {
}
