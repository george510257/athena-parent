package com.gls.athena.starter.amap.config;

import com.gls.athena.starter.amap.support.AmapJsonDecoder;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;

/**
 * 高德配置
 *
 * @author george
 */
public class AmapClientConfig {
    /**
     * 高德json解码器
     *
     * @return Decoder
     */
    @Bean
    public Decoder amapJsonDecoder() {
        return new AmapJsonDecoder();
    }

    /**
     * 请求拦截器
     *
     * @param amapProperties 高德配置
     * @return RequestInterceptor
     */
    @Bean
    public RequestInterceptor requestInterceptor(AmapProperties amapProperties) {
        return requestTemplate -> {
            // 如果没有设置key，则使用默认key
            if (amapProperties.getKey() != null) {
                requestTemplate.query("key", amapProperties.getKey());
            }
        };
    }
}