package com.athena.starter.web.support;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * RequestBodyInputStream 用于解决流只能读取一次的问题
 */
public class RequestBodyInputStream extends ServletInputStream {
    /**
     * 输入流
     */
    private final InputStream delegate;

    /**
     * 构造函数
     *
     * @param body 请求体
     */
    public RequestBodyInputStream(byte[] body) {
        this.delegate = new ByteArrayInputStream(body);
    }

    /**
     * 是否已经读取完毕
     *
     * @return 是否已经读取完毕
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * 是否准备就绪
     *
     * @return 是否准备就绪
     */
    @Override
    public boolean isReady() {
        return true;
    }

    /**
     * 设置读取监听器
     *
     * @param readListener 读取监听器
     */
    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    /**
     * 读取
     *
     * @return 读取
     * @throws IOException IO异常
     */
    @Override
    public int read() throws IOException {
        return this.delegate.read();
    }

    /**
     * 读取
     *
     * @param b   byte数组
     * @param off 读取的起始位置
     * @param len 读取的长度
     * @return 读取的长度
     * @throws IOException IO异常
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.delegate.read(b, off, len);
    }

    /**
     * 读取
     *
     * @param b byte数组
     * @return 读取的长度
     * @throws IOException IO异常
     */
    @Override
    public int read(byte[] b) throws IOException {
        return this.delegate.read(b);
    }

    /**
     * 跳过
     *
     * @param n 跳过的长度
     * @return 跳过的长度
     * @throws IOException IO异常
     */
    @Override
    public long skip(long n) throws IOException {
        return this.delegate.skip(n);
    }

    /**
     * 可读取的长度
     *
     * @return 可读取的长度
     * @throws IOException IO异常
     */
    @Override
    public int available() throws IOException {
        return this.delegate.available();
    }

    /**
     * 关闭
     *
     * @throws IOException IO异常
     */
    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    /**
     * 标记
     *
     * @param readlimit 读取限制
     */
    @Override
    public synchronized void mark(int readlimit) {
        this.delegate.mark(readlimit);
    }

    /**
     * 重置
     *
     * @throws IOException IO异常
     */
    @Override
    public synchronized void reset() throws IOException {
        this.delegate.reset();
    }

    /**
     * 是否支持标记
     *
     * @return 是否支持标记
     */
    @Override
    public boolean markSupported() {
        return this.delegate.markSupported();
    }

}
