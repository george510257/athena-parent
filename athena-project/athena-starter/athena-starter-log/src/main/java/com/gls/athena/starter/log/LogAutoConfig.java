package com.gls.athena.starter.log;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class LogAutoConfig {
}
