package com.gls.athena.starter.aliyun.core.config;

import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".aliyun")
public class AliyunCoreProperties extends BaseProperties {
    /**
     * 客户端配置
     */
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
     */
    @Data
    public static class Client implements Serializable {
        /**
         * 认证模式
         */
        private AuthMode authMode = AuthMode.AS_AK;
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
