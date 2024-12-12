package com.gls.athena.starter.aliyun.core.config;

import com.gls.athena.common.core.constant.BaseProperties;
import com.gls.athena.common.core.constant.IConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云核心配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = IConstants.BASE_PROPERTIES_PREFIX + ".aliyun")
public class AliyunCoreProperties extends BaseProperties {
    /**
     * 客户端配置
     */
    @NestedConfigurationProperty
    private Map<String, Client> clients = new HashMap<>();

    /**
     * 认证模式
     */
    public enum AuthMode implements Serializable {
        /**
         * AS AK
         */
        AS_AK,
        /**
         * STS
         */
        STS
    }

    /**
     * 客户端配置
     *
     * @author george
     */
    @Data
    public static class Client implements Serializable {
        /**
         * 认证模式
         */
        private AliyunCoreProperties.AuthMode authMode = AliyunCoreProperties.AuthMode.AS_AK;
        /**
         * 区域ID
         */
        private String regionId;
        /**
         * 访问密钥ID
         */
        private String accessKeyId;
        /**
         * 访问密钥密钥
         */
        private String accessKeySecret;
        /**
         * 安全令牌
         */
        private String securityToken;

    }
}
