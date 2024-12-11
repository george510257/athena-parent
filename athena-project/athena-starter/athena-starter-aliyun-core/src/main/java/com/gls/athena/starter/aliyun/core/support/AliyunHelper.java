package com.gls.athena.starter.aliyun.core.support;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.gls.athena.starter.aliyun.core.config.AliyunCoreProperties;
import lombok.experimental.UtilityClass;

/**
 * 阿里云工具类
 *
 * @author george
 */
@UtilityClass
public class AliyunHelper {

    public IAcsClient createAcsClient(AliyunCoreProperties.Client client) {

        // 根据认证模式创建客户端
        if (AliyunCoreProperties.AuthMode.AS_AK.equals(client.getAuthMode())) {
            DefaultProfile profile = DefaultProfile.getProfile(client.getRegionId(),
                    client.getAccessKeyId(), client.getAccessKeySecret());
            return new DefaultAcsClient(profile);
        }
        if (AliyunCoreProperties.AuthMode.STS.equals(client.getAuthMode())) {
            DefaultProfile profile = DefaultProfile.getProfile(client.getRegionId(),
                    client.getAccessKeyId(), client.getAccessKeySecret(), client.getSecurityToken());
            return new DefaultAcsClient(profile);
        }
        // 不支持的认证模式
        throw new IllegalArgumentException("不支持的认证模式");
    }
}
