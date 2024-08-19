package com.athena.security.core.properties;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security")
public class CoreSecurityProperties extends BaseProperties {
    /**
     * 忽略的 URL
     */
    private String[] ignoreUrls = new String[]{"/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico", "/actuator/**", "/error"};
    /**
     * 表单登录
     */
    private FormLogin formLogin = new FormLogin();

    /**
     * 表单登录
     */
    @Data
    public static class FormLogin implements Serializable {
        /**
         * 登录页面
         */
        private String loginPage = "/login.html";
        /**
         * 登录处理 URL
         */
        private String loginProcessingUrl = "/api/login";
        /**
         * 用户名参数
         */
        private String usernameParameter = "username";
        /**
         * 密码参数
         */
        private String passwordParameter = "password";
        /**
         * 成功跳转地址
         */
        private String successForwardUrl = "/";
        /**
         * 失败跳转地址
         */
        private String failureForwardUrl = "/login.html?error";
    }
}
