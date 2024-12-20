package com.gls.athena.starter.amap;

import com.gls.athena.starter.amap.config.AmapClientConfig;
import com.gls.athena.starter.amap.config.AmapProperties;
import com.gls.athena.starter.amap.config.IAmapConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 高德地图自动配置
 *
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(AmapProperties.class)
@EnableFeignClients(basePackages = IAmapConstants.FEIGN_PACKAGE, defaultConfiguration = AmapClientConfig.class)
public class AmapAutoConfig {
}
