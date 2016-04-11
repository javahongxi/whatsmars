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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
