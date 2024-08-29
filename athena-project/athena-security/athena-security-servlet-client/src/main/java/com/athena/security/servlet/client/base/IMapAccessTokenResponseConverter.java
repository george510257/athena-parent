package com.athena.security.servlet.client.base;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.Map;
import java.util.function.Predicate;

/**
 * accessToken 响应转换器
 */
public interface IMapAccessTokenResponseConverter extends Converter<Map<String, Object>, OAuth2AccessTokenResponse>, Predicate<String> {
}
