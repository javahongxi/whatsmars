package org.hongxi.whatsmars.tomcat.connector;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shenhongxi on 16/4/11.
 * Extends InputStream to be more efficient reading lines during HTTP header processing.
 */
public class SocketInputStream extends InputStream {

    /**
     * Underlying input stream.
     */
    private InputStream input;

    /**
     * Internal buffer.
     */
    protected byte[] buf;


    /**
     * Last valid byte.
     */
    protected int count;


    /**
     * Position in the buffer.
     */
    protected int pos;

    public SocketInputStream(InputStream input, int bufferSize) {
        this.input = input;
        this.buf = new byte[bufferSize];
    }

    // input => buf => HttpRequestLine
    public void readRequestLine(HttpRequestLine requestLine) throws IOException {
        // Recycling check
        if (requestLine.methodEnd != 0)
            requestLine.recycle();

        // Checking for a blank line

        // Reading the method name

        // Reading URI

        // Reading protocol
    }

    // input => buf => HttpHeader
    public void readHeader(HttpHeader header) throws IOException {
        // Recycling check
        if (header.nameEnd != 0)
            header.recycle();

        // Checking for a blank line

        // Reading the header name

        // Reading the header value (which can be spanned over multiple lines)
    }

    @Override
    public int read() throws IOException {
        if (pos >= count) {
            fill();
            if (pos >= count)
                return -1;
        }
        return buf[pos++] & 0xff;
    }

    /**
     * Fill the internal buffer using data from the undelying input stream.
     */
    protected void fill()
            throws IOException {
        pos = 0;
        count = 0;
        int nRead = input.read(buf, 0, buf.length);
        if (nRead > 0) {
            count = nRead;
        }
    }
}
