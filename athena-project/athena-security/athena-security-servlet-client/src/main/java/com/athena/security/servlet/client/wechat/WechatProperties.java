package com.athena.security.servlet.client.wechat;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
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
     * 公众号
     */
    private Mp mp = new Mp();
    /**
     * 开放平台
     */
    private Open open = new Open();
    /**
     * 企业微信
     */
    private Work work = new Work();

    /**
     * 微信公众号
     */
    @Data
    public static class Mp implements Serializable {
        /**
         * 注册标识
         */
        private String registrationId = "wechat_mp";
        /**
         * 授权 URI
         */
        private String authorizationUri = "https://open.weixin.qq.com/connect/oauth2/authorize";
        /**
         * 令牌 URI
         */
        private String tokenUri = "https://api.weixin.qq.com/sns/oauth2/access_token";
        /**
         * 用户信息 URI
         */
        private String userInfoUri = "https://api.weixin.qq.com/sns/userinfo";
        /**
         * 用户名属性
         */
        private String userNameAttribute = "openid";
        /**
         * 客户端名称
         */
        private String clientName = "微信公众号";
        /**
         * 作用域
         */
        private List<String> scopes = List.of("snsapi_userinfo");
    }

    /**
     * 微信开放平台
     */
    @Data
    public static class Open implements Serializable {
        /**
         * 注册标识
         */
        private String registrationId = "wechat_open";
        /**
         * 授权 URI
         */
        private String authorizationUri = "https://open.weixin.qq.com/connect/qrconnect";
        /**
         * 令牌 URI
         */
        private String tokenUri = "https://api.weixin.qq.com/sns/oauth2/access_token";
        /**
         * 用户信息 URI
         */
        private String userInfoUri = "https://api.weixin.qq.com/sns/userinfo";
        /**
         * 用户名属性
         */
        private String userNameAttribute = "openid";
        /**
         * 客户端名称
         */
        private String clientName = "微信开放平台";
        /**
         * 作用域
         */
        private List<String> scopes = List.of("snsapi_login");
    }

    /**
     * 企业微信
     */
    @Data
    public static class Work implements Serializable {
        /**
         * 注册标识
         */
        private String registrationId = "wechat_work";
        /**
         * 授权 URI
         */
        private String authorizationUri = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect";
        /**
         * 令牌 URI
         */
        private String tokenUri = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        /**
         * 用户信息 URI
         */
        private String userInfoUri = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
        /**
         * 用户名属性
         */
        private String userNameAttribute = "UserId";
        /**
         * 客户端名称
         */
        private String clientName = "企业微信";
        /**
         * 作用域
         */
        private List<String> scopes = List.of("snsapi_base");
    }
}
