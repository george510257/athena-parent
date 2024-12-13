package com.gls.athena.starter.amap;

import com.gls.athena.starter.amap.config.AmapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
public class AmapAutoConfig {
}
