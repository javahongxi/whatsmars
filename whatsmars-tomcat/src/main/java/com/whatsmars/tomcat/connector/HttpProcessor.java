package com.whatsmars.tomcat.connector;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by shenhongxi on 16/4/11.
 */
public class HttpProcessor {

    private HttpConnector connector;

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public void process(Socket socket) {
        SocketInputStream input = null;
        OutputStream output = null;
        try {
            input = new SocketInputStream(socket.getInputStream(), 2048);
            output = socket.getOutputStream();

            // create HttpRequest object and parse
//            request = new HttpRequest(input);
//            response = new HttpResponse(output);
//            response.setRequest(request);
//            response.setHeaders("Server", "Mars Servlet Container");
            
            parseRequest(input, output);
            parseHeaders(input);
            
//            Processor processor = null;
//            if (request.getRequestURI().startsWith("/servlet/")) {
//                processor = new ServletProcessor();
//            } else {
//                processor = new StaticResourceProcessor();
//            }
//            processor.process(request, response);
            
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders(SocketInputStream input) {
    }

    private void parseRequest(SocketInputStream input, OutputStream output) {
                
    }
}
