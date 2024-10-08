package com.athena.starter.web.support;

import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.WebUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RequestBodyWrapper 用于解决流只能读取一次的问题
 *
 * @author george
 */
public class RequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 输入流
     */
    private ServletInputStream inputStream;

    /**
     * 读取器
     */
    private BufferedReader reader;

    /**
     * 构造函数
     *
     * @param request 请求
     */
    public RequestBodyWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException IO异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            this.inputStream = new RequestBodyInputStream(IoUtil.readBytes(super.getInputStream()));
        }
        return this.inputStream;
    }

    /**
     * 获取字符编码
     *
     * @return 字符编码
     */
    @Override
    public String getCharacterEncoding() {
        String enc = super.getCharacterEncoding();
        return (enc != null ? enc : WebUtils.DEFAULT_CHARACTER_ENCODING);
    }

    /**
     * 获取读取器
     *
     * @return 读取器
     * @throws IOException IO异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        if (this.reader == null) {
            this.reader = new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
        }
        return this.reader;
    }
}
