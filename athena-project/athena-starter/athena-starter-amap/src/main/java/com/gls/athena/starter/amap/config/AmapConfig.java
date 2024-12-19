package com.gls.athena.starter.amap.config;

import com.gls.athena.starter.amap.support.AmapJsonDecoder;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;

/**
 * 高德配置
 *
 * @author george
 */
public class AmapConfig {

    @Bean
    public Decoder amapJsonDecoder() {
        return new AmapJsonDecoder();
    }
}