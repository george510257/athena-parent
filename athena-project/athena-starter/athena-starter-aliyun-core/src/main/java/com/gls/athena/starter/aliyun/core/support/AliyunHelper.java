package com.gls.athena.starter.aliyun.core.support;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
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
    /**
     * 创建AcsClient 客户端
     *
     * @param client 阿里云客户端配置
     * @return AcsClient
     */
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

    /**
     * 获取AssumeRole 凭证
     *
     * @param client  阿里云客户端配置
     * @param request AssumeRoleRequest 请求
     * @return AssumeRoleResponse 凭证
     */
    public AssumeRoleResponse getAssumeRole(AliyunCoreProperties.Client client, AssumeRoleRequest request) {
        IAcsClient acsClient = createAcsClient(client);
        try {
            return acsClient.getAcsResponse(request);
        } catch (Exception e) {
            throw new RuntimeException("获取AssumeRole失败", e);
        }
    }
}
