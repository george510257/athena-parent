package com.gls.athena.security.core.properties;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心安全属性配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security")
public class CoreSecurityProperties extends BaseProperties {
    /**
     * 忽略的 URL
     */
    private String[] ignoreUrls = new String[]{"/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico", "/actuator/**", "/error", "/v3/api-docs"};
    /**
     * rest登录配置
     */
    private Rest rest = new Rest();
    /**
     * 验证码配置
     */
    private Captcha captcha = new Captcha();

    /**
     * rest登录配置
     */
    @Data
    public static class Rest implements Serializable {
        /**
         * 登录页面
         */
        private String loginPage = "/login.html";
        /**
         * 登录处理 URL
         */
        private String loginProcessingUrl = "/rest/Login";
        /**
         * 用户名参数名
         */
        private String usernameParameter = "username";
        /**
         * 密码参数名
         */
        private String passwordParameter = "password";
        /**
         * 手机号参数名
         */
        private String mobileParameter = "mobile";

    }

    /**
     * 验证码配置
     */
    @Data
    public static class Captcha implements Serializable {

        /**
         * 短信验证码配置
         */
        private Sms sms = new Sms();

        /**
         * 图形验证码配置
         */
        private Image image = new Image();

    }

    /**
     * 短信验证码配置
     */
    @Data
    public static class Sms implements Serializable {
        /**
         * 验证码长度
         */
        private int length = 6;
        /**
         * 过期时间
         */
        private int expireIn = 600;
        /**
         * 验证码参数名
         */
        private String codeParameterName = "smsCaptcha";
        /**
         * 手机号参数名
         */
        private String targetParameterName = "mobile";
        /**
         * 获取短信验证码url
         */
        private String url = "/captcha/sms";
        /**
         * 需要校验验证码的url
         */
        private List<String> urls = new ArrayList<>();
    }

    /**
     * 图形验证码配置
     */
    @Data
    public static class Image implements Serializable {
        /**
         * 验证码长度
         */
        private int length = 4;
        /**
         * 过期时间
         */
        private int expireIn = 600;
        /**
         * 图形验证码宽度
         */
        private int width = 100;
        /**
         * 图形验证码高度
         */
        private int height = 30;
        /**
         * 图形验证码干扰线数量
         */
        private int lineCount = 150;
        /**
         * 图形验证码字体大小
         */
        private float fontSize = 0.75f;
        /**
         * 验证码参数名
         */
        private String codeParameterName = "imageCaptcha";
        /**
         * 验证码key参数名
         */
        private String targetParameterName = "uuid";
        /**
         * 获取图形验证码url
         */
        private String url = "/captcha/image";
        /**
         * 需要校验验证码的url
         */
        private List<String> urls = new ArrayList<>();
    }
}
