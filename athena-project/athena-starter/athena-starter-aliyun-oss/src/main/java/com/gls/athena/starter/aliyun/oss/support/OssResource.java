package com.gls.athena.starter.aliyun.oss.support;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.*;
import java.net.URI;
import java.net.URL;

/**
 * oss资源
 *
 * @author george
 */
public class OssResource implements WritableResource {
    /**
     * 位置
     */
    private final URI location;
    /**
     * 存储空间名称
     */
    private final String bucketName;
    /**
     * 对象键
     */
    private final String objectKey;
    /**
     * oss
     */
    private final OSS oss;
    /**
     * bean工厂
     */
    private final ConfigurableListableBeanFactory beanFactory;

    /**
     * 构造函数
     *
     * @param location    位置
     * @param beanFactory bean工厂
     */
    public OssResource(String location, ConfigurableListableBeanFactory beanFactory) {
        this.location = URI.create(location);
        this.bucketName = this.location.getHost();
        this.objectKey = this.location.getPath();
        this.oss = beanFactory.getBean(OSS.class);
        this.beanFactory = beanFactory;
    }

    /**
     * 获取输出流
     *
     * @return 输出流
     * @throws IOException IO异常
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        if (isBucket()) {
            throw new IOException("无法写入存储空间");
        }
        OSSObject ossObject = oss.getObject(bucketName, objectKey);
        InputStream inputStream = ossObject.getObjectContent();
        // 读取输入流
        PipedOutputStream outputStream = new PipedOutputStream();
        IoUtil.copy(inputStream, outputStream);
        return outputStream;
    }

    /**
     * 是否是存储空间
     *
     * @return 是否是存储空间
     */
    private boolean isBucket() {
        return StrUtil.isEmpty(objectKey);
    }

    /**
     * 是否可写
     *
     * @return 是否可写
     */
    @Override
    public boolean exists() {
        if (isBucket()) {
            return oss.doesBucketExist(bucketName);
        } else {
            return oss.doesObjectExist(bucketName, objectKey);
        }
    }

    /**
     * 是否可写
     *
     * @return 是否可写
     */
    @Override
    public URL getURL() throws IOException {
        return this.location.toURL();
    }

    /**
     * 获取URI
     *
     * @return URI
     * @throws IOException IO异常
     */
    @Override
    public URI getURI() throws IOException {
        return this.location;
    }

    /**
     * 获取文件
     *
     * @return 文件
     * @throws IOException IO异常
     */
    @Override
    public File getFile() throws IOException {
        throw new UnsupportedOperationException(
                getDescription() + " cannot be resolved to absolute file path");
    }

    /**
     * 内容长度
     *
     * @return 内容长度
     * @throws IOException IO异常
     */
    @Override
    public long contentLength() throws IOException {
        if (isBucket()) {
            return 0;
        }
        return oss.getObjectMetadata(bucketName, objectKey).getContentLength();
    }

    /**
     * 最后修改时间
     *
     * @return 最后修改时间
     * @throws IOException IO异常
     */
    @Override
    public long lastModified() throws IOException {
        if (isBucket()) {
            return 0;
        }
        return oss.getObjectMetadata(bucketName, objectKey).getLastModified().getTime();
    }

    /**
     * 创建相对路径
     *
     * @param relativePath 相对路径
     * @return 资源
     * @throws IOException IO异常
     */
    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return new OssResource(relativePath, beanFactory);
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFilename() {
        return isBucket() ? bucketName : objectKey;
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    @Override
    public String getDescription() {
        return this.location.toString();
    }

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException IO异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (isBucket()) {
            throw new IllegalStateException(
                    "Cannot open an input stream to a bucket: '" + this.location + "'");
        }
        return oss.getObject(bucketName, objectKey).getObjectContent();
    }
}
