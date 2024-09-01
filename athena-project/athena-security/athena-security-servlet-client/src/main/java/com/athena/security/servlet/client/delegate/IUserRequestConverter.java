package com.athena.security.servlet.client.delegate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.function.Predicate;

/**
 * 用户请求转换器
 *
 * @author george
 */
public interface IUserRequestConverter extends Converter<OAuth2UserRequest, RequestEntity<?>>, Predicate<String> {
}
