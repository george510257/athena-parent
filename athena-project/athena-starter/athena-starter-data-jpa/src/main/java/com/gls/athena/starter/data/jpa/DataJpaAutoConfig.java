package com.gls.athena.starter.data.jpa;

import com.gls.athena.common.core.constant.IConstants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA自动配置类
 *
 * @author george
 */
@Configuration
@ComponentScan
@EntityScan(basePackages = IConstants.BASE_PACKAGE_PREFIX + ".**.entity")
@EnableJpaRepositories(basePackages = IConstants.BASE_PACKAGE_PREFIX + ".**.repository")
public class DataJpaAutoConfig {
}