package com.athena.security.servlet.client.weixin;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信配置属性
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security.weixin")
public class WeixinProperties extends BaseProperties {
    /**
     * 飞书应用 id
     */
    private String registrationId = "weixin";
}
