package com.whatsmars.tomcat.connector;

import com.whatsmars.tomcat.servlet.ServletProcessor;
import com.whatsmars.tomcat.servlet.StaticResourceProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by shenhongxi on 16/4/11.
 */
public class HttpProcessor {

    HttpConnector connector;
    HttpRequest request;
    HttpResponse response;

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public void process(Socket socket) {
        SocketInputStream input = null;
        OutputStream output = null;
        try {
            input = new SocketInputStream(socket.getInputStream(), 2048); // 1.读取套接字的输入流
            output = socket.getOutputStream();

            // create HttpRequest object and parse
            request = new HttpRequest(input);
            response = new HttpResponse(output);
            response.setRequest(request);
            response.setHeader("Server", "Mars Servlet Container");
            
            parseRequest(input, output); // 解析请求行，即HTTP请求的第一行内容
            parseHeaders(input); // 解析请求头
            // request.addHeader(name, value); // 将请求头的名/值添加到request对象的HashMap请求头中

            if (request.getUri().startsWith("/servlet/")) {
                ServletProcessor processor = new ServletProcessor();
                //processor.process(request, response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                //processor.process(request, response);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders(SocketInputStream input) {
        int line = -1;
        while (line != -1) { // 一行一行解析完hear
            HttpHeader httpHeader = new HttpHeader();
            input.readHeader(httpHeader);
        }
    }

    private void parseRequest(SocketInputStream input, OutputStream output) {
        StringBuffer requestStr = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            requestStr.append((char) buffer[j]);
        }
        System.out.println(requestStr.toString());
        String uri = parseUri(requestStr.toString());
        request.setUri(uri);

        // 填充HttpRequest对象
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
}
