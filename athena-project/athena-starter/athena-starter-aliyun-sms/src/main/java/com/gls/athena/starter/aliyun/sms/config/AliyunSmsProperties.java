package com.gls.athena.starter.aliyun.sms.config;

import com.gls.athena.common.core.constant.IConstants;
import com.gls.athena.starter.aliyun.core.config.AliyunCoreProperties;
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
@ConfigurationProperties(prefix = IConstants.BASE_PROPERTIES_PREFIX + ".aliyun.sms")
public class AliyunSmsProperties extends AliyunCoreProperties.Client {
    /**
     * 签名名称
     */
    private String signName;
}
