package com.gls.athena.starter.aliyun.oss.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.aliyun.oss.OSS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * oss资源
 * <p>
 * 实现{@link WritableResource}接口 用于读取和写入阿里云对象存储服务（OSS）中的对象
 * </p>
 * <p>
 * 例如：
 * <li>oss://bucketName/objectKey</li>
 * <li>oss://bucketName</li>
 * </p>
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
     * 构造函数
     *
     * @param location 位置
     */
    public OssResource(String location) {
        this.location = URI.create(location);
        this.bucketName = this.location.getAuthority();
        if (StrUtil.isEmpty(this.location.getPath())) {
            this.objectKey = "";
        } else {
            this.objectKey = this.location.getPath().substring(1);
        }
    }

    /**
     * 获取输出流
     *
     * @return 输出流
     * @throws IOException IO异常
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        if (exists()) {
            throw new IOException("文件已存在");
        }
        // 创建管道流
        final PipedInputStream inputStream = new PipedInputStream();
        final PipedOutputStream outputStream = new PipedOutputStream(inputStream);
        // 获取oss任务执行器
        ExecutorService ossTaskExecutor = SpringUtil.getBean(ExecutorService.class);
        ossTaskExecutor.submit(() -> {
            try {
                // 上传文件
                OSS oss = SpringUtil.getBean(OSS.class);
                oss.putObject(bucketName, objectKey, inputStream);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return outputStream;
    }

    /**
     * 是否是存储空间
     *
     * @return 是否是存储空间
     */
    private boolean isBucket() {
        // 判断对象键是否为空
        return StrUtil.isEmpty(objectKey);
    }

    /**
     * 是否可写
     *
     * @return 是否可写
     */
    @Override
    public boolean exists() {
        // 判断是否是存储空间
        OSS oss = SpringUtil.getBean(OSS.class);
        if (isBucket()) {
            // 存储空间是否存在
            return oss.doesBucketExist(bucketName);
        } else {
            // 对象是否存在
            return oss.doesObjectExist(bucketName, objectKey);
        }
    }

    /**
     * 获取URL
     *
     * @return URL
     * @throws IOException IO异常
     */
    @Override
    public URL getURL() throws IOException {
        // 返回URL
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
        // 返回位置
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
        // 抛出异常
        throw new UnsupportedOperationException(getDescription() + " 无法解析为绝对文件路径");
    }

    /**
     * 内容长度
     *
     * @return 内容长度
     * @throws IOException IO异常
     */
    @Override
    public long contentLength() throws IOException {
        // 判断是否是存储空间
        if (isBucket()) {
            return 0;
        }
        // 获取对象元数据
        OSS oss = SpringUtil.getBean(OSS.class);
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
        // 判断是否是存储空间
        if (isBucket()) {
            return 0;
        }
        // 获取对象元数据
        OSS oss = SpringUtil.getBean(OSS.class);
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
        // 创建oss资源
        return new OssResource(relativePath);
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFilename() {
        // 返回存储空间名称或对象键
        return isBucket() ? bucketName : objectKey;
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    @Override
    public String getDescription() {
        // 返回位置
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
        // 判断是否存在
        if (exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        // 判断是否是存储空间
        if (isBucket()) {
            throw new IllegalStateException("无法打开输入流到存储空间: '" + this.location + "'");
        }
        // 获取对象
        OSS oss = SpringUtil.getBean(OSS.class);
        return oss.getObject(bucketName, objectKey).getObjectContent();
    }
}
