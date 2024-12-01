package com.gls.athena.starter.web.support;

import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RequestBodyWrapper 用于解决流只能读取一次的问题
 *
 * @author george
 */
@Slf4j
public class RequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 请求体
     */
    private final String body;

    /**
     * 构造函数
     *
     * @param request 请求
     */
    public RequestBodyWrapper(HttpServletRequest request) {
        super(request);
        this.body = getBodyString(request);
    }

    /**
     * 获取请求体字符串
     *
     * @param request 请求
     * @return 请求体字符串
     */
    private String getBodyString(HttpServletRequest request) {
        try {
            return IoUtil.read(request.getInputStream(), getCharacterEncoding());
        } catch (IOException e) {
            log.error("获取请求体失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException IO异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 字节数组输入流
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body.getBytes());
        // 返回输入流
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() {
                return inputStream.read();
            }
        };
    }

    /**
     * 获取读取器
     *
     * @return 读取器
     * @throws IOException IO异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        // 返回读取器
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }
}
