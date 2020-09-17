package org.hongxi.whatsmars.sentinel.okhttp.controller;

import com.alibaba.csp.sentinel.adapter.okhttp.SentinelOkHttpConfig;
import com.alibaba.csp.sentinel.adapter.okhttp.SentinelOkHttpInterceptor;
import com.alibaba.csp.sentinel.adapter.okhttp.fallback.DefaultOkHttpFallback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author shen.hongxi
 */
@RestController
@RequestMapping("/okhttp")
public class OkHttpTestController {

    @Value("${server.port}")
    private Integer port;

    private final OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new SentinelOkHttpInterceptor(new SentinelOkHttpConfig((request, connection) -> {
            String regex = "/okhttp/back/";
            String url = request.url().toString();
            if (url.contains(regex)) {
                url = url.substring(0, url.indexOf(regex) + regex.length()) + "{id}";
            }
            return request.method() + ":" + url;
        }, new DefaultOkHttpFallback())))
        .build();

    @RequestMapping("/back")
    public String back() {
        return "Welcome Back!";
    }

    @RequestMapping("/back/{id}")
    public String back(@PathVariable String id) {
        return "Welcome Back! " + id;
    }

    @RequestMapping("/testcase/{id}")
    public String testcase(@PathVariable String id) throws Exception {
        return getRemoteString(id);
    }

    @RequestMapping("/testcase")
    public String testcase() throws Exception {
        return getRemoteString(null);
    }

    private String getRemoteString(String id) throws IOException {
        Request request = new Request.Builder()
            .url("http://localhost:" + port + "/okhttp/back" + (id == null ? "" : "/" + id))
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}