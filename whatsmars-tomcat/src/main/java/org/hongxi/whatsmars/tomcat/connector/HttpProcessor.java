package org.hongxi.whatsmars.tomcat.connector;

import org.hongxi.whatsmars.tomcat.servlet.StaticResourceProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by shenhongxi on 16/4/11.
 */
public class HttpProcessor {

    private HttpConnector connector;
    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine = new HttpRequestLine();

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public void process(Socket socket) {
        SocketInputStream input = null;
        OutputStream output = null;
        try {
            input = new SocketInputStream(socket.getInputStream(), connector.getBufferSize()); // 1.读取套接字的输入流
            output = socket.getOutputStream();

            // create HttpRequest object and parse
            request = new HttpRequest(input);
            response = new HttpResponse(output);
            response.setRequest(request);
            response.setHeader("Server", "Mars Servlet Container");
            
            parseRequest(input, output); // 解析请求行，即HTTP请求的第一行内容
            parseHeaders(input); // 解析请求头

            if (request.getRequestURI().startsWith("/servlet/")) {
                connector.getContainer().invoke((HttpServletRequest) request, (HttpServletResponse) response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                //processor.process(request, response);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders(SocketInputStream input) throws IOException, ServletException{
        while (true) { // 一行一行解析完header
            HttpHeader header = new HttpHeader();
            // Read the next header
            input.readHeader(header);
            if (header.nameEnd == 0) {
                if (header.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException("httpProcessor parseHeaders colon");
                }
            }
            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);
            request.addHeader(name, value);
            // do something for some headers, ignore others.
            if (name.equals("cookie")) {
                // ...
                // request.addCookie(cookies[i]);
            } else if (name.equals("content-length")) {
                int n = -1;
                try {
                    n = Integer.parseInt(value);
                } catch (Exception e) {
                    throw new ServletException("httpProcessor.parseHeaders.contentLength");
                }
                request.setContentLength(n);
            } else if (name.equals("content-type")) {
                request.setContentType(value);
            }
        }
    }

    private void parseRequest(SocketInputStream input, OutputStream output) throws IOException, ServletException {
        input.readRequestLine(requestLine);

        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        // Validate the incoming request line
        if (method.length() < 1) {
            throw new ServletException("Missing HTTP request method");
        } else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request URI");
        }
        // Parse any query parameters out of the request URI
        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1,
                    requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }
        String normalizedUri = normalize(uri);

        ((HttpRequest) request).setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            ((HttpRequest) request).setRequestURI(normalizedUri);
        }
        else {
            ((HttpRequest) request).setRequestURI(uri);
        }

        if (normalizedUri == null) {
            throw new ServletException("Invalid URI: " + uri + "'");
        }
    }

    // Return a context-relative path, beginning with a "/"
    protected String normalize(String path) {
        if (path == null) return null;
        String normalized = path;
        // ...
        return path;
    }
}
