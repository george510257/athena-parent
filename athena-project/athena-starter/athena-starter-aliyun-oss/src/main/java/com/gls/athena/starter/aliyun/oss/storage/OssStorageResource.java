package com.gls.athena.starter.aliyun.oss.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Executor;

/**
 * oss 资源适配
 *
 * @author george
 */
@Slf4j
@RequiredArgsConstructor
public class OssStorageResource implements WritableResource {
    /**
     * OSS 客户端
     */
    private final OSS oss;
    /**
     * OSS 任务执行器
     */
    private final Executor executor;
    /**
     * OSS 存储位置
     */
    private final OssStorageLocation location;

    /**
     * 获取输出流
     *
     * @return 输出流
     * @throws IOException IO异常
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        // 如果是存储桶，则抛出异常
        if (location.isBucket()) {
            throw new FileNotFoundException("Cannot open an output stream to a bucket");
        }
        // 如果对象存在，则抛出异常
        if (oss.doesObjectExist(location.getBucketName(), location.getFileName())) {
            throw new FileNotFoundException("The specified key already exists");
        }
        // 创建管道输入流
        final PipedInputStream inputStream = new PipedInputStream();
        final OutputStream outputStream = new PipedOutputStream(inputStream);
        // 执行 OSS 任务
        executor.execute(() -> {
            try {
                oss.putObject(location.getBucketName(), location.getFileName(), inputStream);
            } catch (Exception e) {
                log.error("Failed to write to OSS", e);
            }
        });
        return outputStream;
    }

    /**
     * 是否存在
     *
     * @return 是否存在
     */
    @Override
    public boolean exists() {
        if (location.isBucket()) {
            return oss.doesBucketExist(location.getBucketName());
        }
        return oss.doesObjectExist(location.getBucketName(), location.getFileName());
    }

    /**
     * 获取 URL
     *
     * @return URL
     * @throws IOException IO异常
     */
    @Override
    public URL getURL() throws IOException {
        return location.getUri().toURL();
    }

    /**
     * 获取 URI
     *
     * @return URI
     * @throws IOException IO异常
     */
    @Override
    public URI getURI() throws IOException {
        return location.getUri();
    }

    /**
     * 获取文件
     *
     * @return 文件
     * @throws IOException IO异常
     */
    @Override
    public File getFile() throws IOException {
        throw new UnsupportedEncodingException("OSS object cannot be resolved to a file");
    }

    /**
     * 获取内容长度
     *
     * @return 内容长度
     */
    @Override
    public long contentLength() throws IOException {
        if (getOssObject() != null) {
            return getOssObject().getObjectMetadata().getContentLength();
        }
        return 0;
    }

    /**
     * 获取最后修改时间
     *
     * @return 最后修改时间
     * @throws IOException IO异常
     */
    @Override
    public long lastModified() throws IOException {
        if (getOssObject() != null) {
            return getOssObject().getObjectMetadata().getLastModified().getTime();
        }
        return 0;
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
        OssStorageLocation relativeLocation = OssStorageLocation.ofLocation(relativePath);
        return new OssStorageResource(oss, executor, relativeLocation);
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFilename() {
        if (location.isBucket()) {
            return location.getBucketName();
        }
        return location.getFileName();
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    @Override
    public String getDescription() {
        return location.toString();
    }

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException IO异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (getOssObject() != null) {
            return getOssObject().getObjectContent();
        }
        return null;
    }

    /**
     * 获取 OSS 对象
     *
     * @return OSS 对象
     */
    private OSSObject getOssObject() {
        if (location.isBucket()) {
            return null;
        }
        return oss.getObject(location.getBucketName(), location.getFileName());
    }
}
