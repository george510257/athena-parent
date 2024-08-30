package com.athena.security.servlet.client.delegate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;
import java.util.function.Predicate;

public interface IUserResponseConverter extends Converter<OAuth2UserRequest, Converter<Map<String, Object>, Map<String, Object>>>, Predicate<String> {
}
