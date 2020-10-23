package org.hongxi.whatsmars.boot.sample.web.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by shenhongxi on 2020/10/23.
 */
public class SampleHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] jsonBytes;

    public SampleHttpServletRequestWrapper(HttpServletRequest request, byte[] jsonBytes) {
        super(request);
        this.jsonBytes = jsonBytes;
    }

    @Override
    public String getContentType() {
        if (super.getContentType() == null) {
            return MediaType.APPLICATION_JSON_VALUE;
        }
        if (super.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            return MediaType.APPLICATION_JSON_VALUE;
        }
        return super.getContentType();
    }

    @Override
    public String getHeader(String name) {
        if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(name)) {
            return getContentType();
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(name)) {
            return Collections.enumeration(Arrays.asList(getContentType()));
        }
        return super.getHeaders(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(jsonBytes), StandardCharsets.UTF_8));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BodyServletInputStream(jsonBytes);
    }
}
