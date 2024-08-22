package com.athena.security.core.properties;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 核心安全属性配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".security")
public class CoreSecurityProperties extends BaseProperties {
    /**
     * 忽略的 URL
     */
    private String[] ignoreUrls = new String[]{"/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico", "/actuator/**", "/error"};
    /**
     * rest登录配置
     */
    private Rest rest = new Rest();

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
        private String loginProcessingUrl = "/api/restLogin";

    }

}
