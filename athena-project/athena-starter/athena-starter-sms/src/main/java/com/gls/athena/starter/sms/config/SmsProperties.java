package com.gls.athena.starter.sms.config;

import com.gls.athena.common.core.constant.BaseProperties;
import com.gls.athena.common.core.constant.IConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 短信配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = IConstants.BASE_PROPERTIES_PREFIX + ".sms")
public class SmsProperties extends BaseProperties {
    /**
     * kafka配置
     */
    private Kafka kafka = new Kafka();

    /**
     * kafka配置
     */
    @Data
    public static class Kafka implements Serializable {
        /**
         * 主题
         */
        private String topic = "athena-sms";
        /**
         * key
         */
        private String key = "sms";
    }
}
