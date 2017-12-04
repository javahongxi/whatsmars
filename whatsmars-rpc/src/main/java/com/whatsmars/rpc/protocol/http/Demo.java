package com.whatsmars.rpc.protocol.http;

import com.whatsmars.grpc.service.HelloResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shenhongxi on 2017/6/14.
 */
public class Demo {

    public static void main(String[] args) {

    }

    public static String post(String url, byte[] args, String requestName) throws Exception {
        String result = null;

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Request-Name", requestName);
        conn.setConnectTimeout(30 * 1000);
        conn.setReadTimeout(30 * 1000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.write(args);
        os.close();

        if (200 == conn.getResponseCode()) {
            InputStream in = conn.getInputStream();
            byte[] bytes1 = new byte[conn.getContentLength()];
            in.read(bytes1);

            HelloResponse orderResponse = HelloResponse.parseFrom(bytes1);
            result = orderResponse.toString();
        }

        conn.disconnect();

        return result;
    }
}
