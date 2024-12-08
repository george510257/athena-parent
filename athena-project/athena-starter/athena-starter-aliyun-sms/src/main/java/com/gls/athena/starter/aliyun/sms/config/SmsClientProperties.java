package com.gls.athena.starter.aliyun.sms.config;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信客户端配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".aliyun.sms.client")
public class SmsClientProperties extends BaseProperties {
    /**
     * accessKeyId
     */
    private String accessKeyId;
    /**
     * accessKeySecret
     */
    private String accessKeySecret;
    /**
     * endpoint
     */
    private String endpoint;
}
