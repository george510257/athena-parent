package com.gls.athena.starter.aliyun.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 阿里云oss配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".aliyun.oss")
public class AliyunOssProperties extends BaseProperties {
    /**
     * 授权类型
     */
    private AuthType authType = AuthType.AS_AK;
    /**
     * 阿里云OSS服务的Endpoint
     */
    private String endpoint;
    /**
     * 存储空间名称
     */
    private String bucketName;
    /**
     * 文件路径前缀
     */
    private String pathPrefix;
    /**
     * 访问密钥ID
     */
    private String accessKeyId;
    /**
     * 访问密钥
     */
    private String accessKeySecret;
    /**
     * SecurityToken
     */
    private String securityToken;
    /**
     * 配置
     */
    @NestedConfigurationProperty
    private ClientBuilderConfiguration config = new ClientBuilderConfiguration();

    /**
     * 授权类型
     */
    public enum AuthType {
        /**
         * AS_AK
         */
        AS_AK,
        /**
         * STS
         */
        STS
    }
}
