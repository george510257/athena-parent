package com.athena.starter.aliyun.oss.client;

import cn.hutool.extra.spring.SpringUtil;
import com.aliyun.oss.OSS;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.InputStream;

/**
 * oss工具类
 */
@UtilityClass
public class OssUtil {

    /**
     * 上传文件
     *
     * @param path 文件路径
     * @param file 文件
     */
    public void upload(String path, File file) {
        getOssClient().putObject(getBucketName(), path, file);
    }

    /**
     * 上传文件
     *
     * @param path        文件路径
     * @param inputStream 文件流
     */
    public void upload(String path, InputStream inputStream) {
        getOssClient().putObject(getBucketName(), path, inputStream);
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     */
    public void delete(String path) {
        getOssClient().deleteObject(getBucketName(), path);
    }

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @return 文件流
     */
    public InputStream download(String path) {
        return getOssClient().getObject(getBucketName(), path).getObjectContent();
    }

    /**
     * 获取stsToken
     *
     * @return AssumeRoleResponse
     */
    public AssumeRoleResponse getStsToken() {
        try {
            OssClientProperties.Acs acs = SpringUtil.getBean(OssClientProperties.class).getAcs();
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(acs.getRoleArn());
            request.setRoleSessionName(acs.getRoleSessionName());
            request.setPolicy(acs.getPolicy());
            request.setDurationSeconds(acs.getDurationSeconds());
            return getAcsClient().getAcsResponse(request);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取oss bucketName
     *
     * @return bucketName
     */
    private String getBucketName() {
        return SpringUtil.getBean(OssClientProperties.class).getBucketName();
    }

    /**
     * 获取oss客户端
     *
     * @return oss客户端
     */
    private OSS getOssClient() {
        return SpringUtil.getBean(OSS.class);
    }

    /**
     * 获取acs客户端
     *
     * @return acs客户端
     */
    private IAcsClient getAcsClient() {
        return SpringUtil.getBean(IAcsClient.class);
    }
}
