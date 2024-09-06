package com.athena.security.servlet.client.delegate;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;

import java.util.function.Predicate;

/**
 * OAuth2AccessTokenResponseClient 定制器
 *
 * @author george
 */
public interface IAuthorizationCodeTokenResponseClientCustomizer
        extends Customizer<DefaultAuthorizationCodeTokenResponseClient>, Predicate<String> {

}
