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
     * 语言
     */
    private String lang = "zh_CN";
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
     * 小程序
     */
    private MiniApp miniApp = new MiniApp();

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
        private String authorizationUri = "https://login.work.weixin.qq.com/wwlogin/sso/login";
        /**
         * 令牌 URI
         */
        private String tokenUri = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        /**
         * 用户登录 URI
         */
        private String userLoginUri = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo";
        /**
         * 用户信息 URI
         */
        private String userInfoUri = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
        /**
         * 用户名属性
         */
        private String userNameAttribute = "id";
        /**
         * 客户端名称
         */
        private String clientName = "企业微信";
        /**
         * 作用域
         */
        private List<String> scopes = List.of("snsapi_base");
        /**
         * 登录类型 ServiceApp: 服务商模式, CorpApp: 企业自建应用
         */
        private String loginType = "CorpApp";
        /**
         * 应用 ID 企业自建应用必填
         */
        private String agentId = "1000002";
        /**
         * 语言
         */
        private String lang = "zh";
    }

    @Data
    public static class MiniApp implements Serializable {
        /**
         * 注册标识
         */
        private String registrationId = "wechat_mini_app";
        /**
         * 授权 URI
         */
        private String authorizationUri = "/login/oauth2/code/wechat_mini_app";
        /**
         * 应用令牌 URI
         */
        private String appAccessTokenUri = "https://api.weixin.qq.com/cgi-bin/token";
        /**
         * 令牌 URI
         */
        private String tokenUri = "https://api.weixin.qq.com/sns/jscode2session";
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
        private String clientName = "微信小程序";
        /**
         * 作用域
         */
        private List<String> scopes = List.of("snsapi_userinfo");
    }
}
