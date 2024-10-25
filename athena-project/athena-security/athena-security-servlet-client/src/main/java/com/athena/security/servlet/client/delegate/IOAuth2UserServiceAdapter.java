package com.athena.security.servlet.client.delegate;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.function.Predicate;

/**
 * OAuth2UserService 定制器
 *
 * @author george
 */
public interface IOAuth2UserServiceAdapter
        extends OAuth2UserService<OAuth2UserRequest, OAuth2User>, Predicate<String> {
}
