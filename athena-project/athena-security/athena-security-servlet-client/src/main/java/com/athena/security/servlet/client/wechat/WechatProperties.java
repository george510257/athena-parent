package com.athena.security.servlet.client.wechat;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 微信配置属性
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security.wechat")
public class WechatProperties extends BaseProperties {
    /**
     * 微信应用 公众号 id
     */
    private String mpRegistrationId = "wechat_mp";
    /**
     * 微信应用 开放平台 id
     */
    private String openRegistrationId = "wechat_open";
    /**
     * 微信公众号 授权码授权 uri
     */
    private String mpAuthorizationUri = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * 微信开放平台 授权码授权 uri
     */
    private String openAuthorizationUri = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 微信用户级别的access_token uri
     */
    private String tokenUri = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 微信用户信息 uri
     */
    private String userInfoUri = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 用户名属性
     */
    private String userNameAttribute = "openid";

    /**
     * 客户端名称
     */
    private String clientName = "微信";

    /**
     * 微信公众号授权范围
     */
    private List<String> mpScopes = List.of("snsapi_userinfo");

    /**
     * 微信开放平台授权范围
     */
    private List<String> openScopes = List.of("snsapi_login");
}
