package com.gls.athena.security.servlet.client.feishu;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 飞书配置属性
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security.feishu")
public class FeishuProperties extends BaseProperties {
    /**
     * 飞书应用 id
     */
    private String registrationId = "feishu";
    /**
     * 飞书应用级别的access_token uri
     */
    private String appAccessTokenUri = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal";

    /**
     * 飞书授权码授权 uri
     */
    private String authorizationUri = "https://open.feishu.cn/open-apis/authen/v1/authorize";

    /**
     * 飞书用户级别的access_token uri
     */
    private String tokenUri = "https://open.feishu.cn/open-apis/authen/v1/oidc/access_token";

    /**
     * 飞书用户信息 uri
     */
    private String userInfoUri = "https://open.feishu.cn/open-apis/authen/v1/user_info";

    /**
     * 用户名属性
     */
    private String userNameAttribute = "union_id";

    /**
     * 客户端名称
     */
    private String clientName = "飞书";

}
