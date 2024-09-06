package com.athena.security.servlet.client.delegate;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import java.util.function.Predicate;

/**
 * OAuth2UserService 定制器
 *
 * @author george
 */
public interface IOAuth2UserServiceCustomizer extends Customizer<DefaultOAuth2UserService>, Predicate<String> {
}
