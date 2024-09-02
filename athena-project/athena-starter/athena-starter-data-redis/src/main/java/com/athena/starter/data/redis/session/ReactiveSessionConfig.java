package com.athena.starter.data.redis.session;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * ReactiveSessionConfig for web application
 *
 * @author george
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableRedisWebSession
public class ReactiveSessionConfig {
}
