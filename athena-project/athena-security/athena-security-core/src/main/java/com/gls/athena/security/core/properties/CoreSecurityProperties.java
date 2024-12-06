package com.gls.athena.security.core.properties;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

}
