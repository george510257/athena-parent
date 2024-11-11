package com.gls.athena.starter.aliyun.oss.client;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.common.core.constant.BaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * oss 动态配置
 *
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = BaseConstants.BASE_PROPERTIES_PREFIX + ".aliyun.oss.client")
public class OssClientProperties extends BaseProperties {

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
    private String filePrefix;
    /**
     * 授权模式
     */
    private Mode mode;
    /**
     * token
     */
    private Token token = new Token();
    /**
     * stsToken
     */
    private StsToken stsToken = new StsToken();
    /**
     * acs
     */
    private Acs acs = new Acs();
    /**
     * 配置
     */
    @NestedConfigurationProperty
    private ClientBuilderConfiguration config = new ClientBuilderConfiguration();

    /**
     * 模式
     */
    public enum Mode {
        /**
         * ak/sk
         */
        AK_SK,
        /**
         * stsToken
         */
        STS
    }

    /**
     * token
     */
    @Data
    public static class Token implements Serializable {
        /**
         * accessKeyId
         */
        private String accessKeyId;
        /**
         * accessKeySecret
         */
        private String accessKeySecret;
    }

    /**
     * stsToken
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class StsToken extends Token {
        /**
         * 安全令牌
         */
        private String securityToken;
    }

    /**
     * acs
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Acs extends Token {
        /**
         * regionId
         */
        private String regionId;
        /**
         * 临时凭证的有效时间，单位是秒
         */
        private Long durationSeconds;
        /**
         * 授权策略
         */
        private String policy;
        /**
         * 角色ARN
         */
        private String roleArn;
        /**
         * 角色会话名称
         */
        private String roleSessionName;
    }
}
