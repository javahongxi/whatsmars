package org.hongxi.whatsmars.boot.sample.web.filter;

import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Created by shenhongxi on 2020/10/23.
 */
public class SampleHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private ContentCachingResponseWrapper responseWrapper;

    private ResponseBodyHandler responseBodyHandler;

    private String responseBody;

    public SampleHttpServletResponseWrapper(HttpServletResponse response,
                            ResponseBodyHandler responseBodyHandler) {
        super(response);
        this.responseWrapper = new ContentCachingResponseWrapper(response);
        this.responseBodyHandler = responseBodyHandler;
    }

    @Override
    public void sendError(int sc) throws IOException {
        responseWrapper.sendError(sc);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void sendError(int sc, String msg) throws IOException {
        responseWrapper.sendError(sc, msg);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        responseWrapper.sendRedirect(location);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return responseWrapper.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return responseWrapper.getWriter();
    }

    @Override
    public void flushBuffer() throws IOException {
        responseWrapper.flushBuffer();
    }

    @Override
    public void setContentLength(int len) {
        responseWrapper.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long len) {
        responseWrapper.setContentLengthLong(len);
    }

    @Override
    public void setBufferSize(int size) {
        responseWrapper.setBufferSize(size);
    }

    @Override
    public void resetBuffer() {
        responseWrapper.resetBuffer();
    }

    @Override
    public void reset() {
        responseWrapper.reset();
    }

    public void copyBodyToResponse() throws IOException {
        byte[] rawContentArray = responseWrapper.getContentAsByteArray();
        this.responseBody = new String(rawContentArray);
        if (rawContentArray != null && rawContentArray.length > 0) {
            byte[] result = responseBodyHandler.handle(rawContentArray);
            write(result);
        }
    }

    public void write(byte[] result) throws IOException {
        HttpServletResponse rawResponse = (HttpServletResponse) getResponse();
        if (!rawResponse.isCommitted()) {
            rawResponse.setContentLength(result.length);
            rawResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        rawResponse.getOutputStream().write(result);
        rawResponse.flushBuffer();
    }

    public byte[] getContentAsByteArray() {
        return responseWrapper.getContentAsByteArray();
    }

    public String getResponseBody() {
        if (Objects.isNull(this.responseBody)) {
            this.responseBody = new String(getContentAsByteArray());
        }
        return responseBody;
    }
}
