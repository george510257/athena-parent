package com.gls.athena.starter.aliyun.oss.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.gls.athena.common.core.constant.BaseConstants;
import com.gls.athena.starter.aliyun.core.config.AliyunCoreProperties;
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
public class AliyunOssProperties extends AliyunCoreProperties.Client {
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
     * 配置
     */
    @NestedConfigurationProperty
    private ClientBuilderConfiguration config = new ClientBuilderConfiguration();

}
