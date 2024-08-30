package com.athena.security.servlet.client.feishu.domian;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 飞书配置属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security.feishu")
public class FeishuProperties extends BaseProperties {
    /**
     * app token url
     */
    private String appAccessTokenUrl = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal";

    /**
     * redis key prefix
     */
    private String cacheName = "feishu_app_access_token";
}
