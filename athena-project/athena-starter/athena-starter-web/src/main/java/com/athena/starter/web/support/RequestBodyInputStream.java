package com.athena.starter.web.support;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestBodyInputStream extends ServletInputStream {

    private final InputStream delegate;

    public RequestBodyInputStream(byte[] body) {
        this.delegate = new ByteArrayInputStream(body);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return this.delegate.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.delegate.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.delegate.read(b);
    }

    @Override
    public long skip(long n) throws IOException {
        return this.delegate.skip(n);
    }

    @Override
    public int available() throws IOException {
        return this.delegate.available();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.delegate.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.delegate.reset();
    }

    @Override
    public boolean markSupported() {
        return this.delegate.markSupported();
    }

}
