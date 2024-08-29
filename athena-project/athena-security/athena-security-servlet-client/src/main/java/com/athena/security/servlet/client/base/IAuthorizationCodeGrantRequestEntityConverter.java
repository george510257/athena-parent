package com.athena.security.servlet.client.base;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;

import java.util.function.Predicate;

/**
 * OAuth2 授权码请求实体转换器
 */
public interface IAuthorizationCodeGrantRequestEntityConverter extends Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>>, Predicate<String> {
}
