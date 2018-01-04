package org.hongxi.whatsmars.tomcat.server;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shenhongxi on 16/3/21.
 */
public class Request {

    private InputStream input;

    private String uri; // 性能考虑，用byte[]

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println(request.toString());
        uri = parseUri(request.toString());
    }

    private String parseUri(String requestStr) {
        // GET /index.html HTTP/1.1
        // Accept: text/plain; text/html
        // ...
        int index1 = requestStr.indexOf(' ');
        int index2;
        if (index1 != -1) {
            index2 = requestStr.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return requestStr.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    public String getUri() {
        return uri;
    }
}
