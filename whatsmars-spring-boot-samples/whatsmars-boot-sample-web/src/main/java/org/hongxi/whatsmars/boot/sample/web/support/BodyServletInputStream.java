package org.hongxi.whatsmars.boot.sample.web.support;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shenhongxi on 2020/10/23.
 */
public class BodyServletInputStream extends ServletInputStream {

    private final InputStream delegate;

    public BodyServletInputStream(byte[] body) {
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
    public synchronized void mark(int readLimit) {
        this.delegate.mark(readLimit);
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