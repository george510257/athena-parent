package com.athena.starter.swagger.config;

import com.athena.common.core.constant.BaseConstants;
import com.athena.common.core.constant.BaseProperties;
import com.athena.starter.swagger.model.OpenApi;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Swagger配置属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".swagger")
public class SwaggerProperties extends BaseProperties {
    /**
     * openApi配置
     */
    @NestedConfigurationProperty
    private OpenApi openApi = new OpenApi();
}
