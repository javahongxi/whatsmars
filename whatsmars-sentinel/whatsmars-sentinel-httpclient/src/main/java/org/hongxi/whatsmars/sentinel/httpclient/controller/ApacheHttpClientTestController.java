package org.hongxi.whatsmars.sentinel.httpclient.controller;

import com.alibaba.csp.sentinel.adapter.apache.httpclient.SentinelApacheHttpClientBuilder;
import com.alibaba.csp.sentinel.adapter.apache.httpclient.config.SentinelApacheHttpClientConfig;
import com.alibaba.csp.sentinel.adapter.apache.httpclient.extractor.ApacheHttpClientResourceExtractor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author shen.hongxi
 */
@RestController
@RequestMapping("/httpclient")
public class ApacheHttpClientTestController {

    @Value("${server.port}")
    private Integer port;

    @RequestMapping("/back")
    public String back() {
        System.out.println("back");
        return "Welcome Back!";
    }

    @RequestMapping("/back/{id}")
    public String back(@PathVariable String id) {
        System.out.println("back");
        return "Welcome Back! " + id;
    }

    @RequestMapping("/sync")
    public String sync() throws Exception {
        SentinelApacheHttpClientConfig config = new SentinelApacheHttpClientConfig();
        config.setExtractor(request -> {
            String contains = "/httpclient/back/";
            String uri = request.getRequestLine().getUri();
            if (uri.startsWith(contains)) {
                uri = uri.substring(0, uri.indexOf(contains) + contains.length()) + "{id}";
            }
            return request.getMethod() + ":" + uri;
        });
        CloseableHttpClient httpclient = new SentinelApacheHttpClientBuilder(config).build();

        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/httpclient/back");
        return getRemoteString(httpclient, httpGet);
    }

    @RequestMapping("/sync/{id}")
    public String sync(@PathVariable String id) throws Exception {
        SentinelApacheHttpClientConfig config = new SentinelApacheHttpClientConfig();
        config.setExtractor(request -> {
            String contains = "/httpclient/back/";
            String uri = request.getRequestLine().getUri();
            if (uri.startsWith(contains)) {
                uri = uri.substring(0, uri.indexOf(contains) + contains.length()) + "{id}";
            }
            return request.getMethod() + ":" + uri;
        });
        CloseableHttpClient httpclient = new SentinelApacheHttpClientBuilder(config).build();

        HttpGet httpGet = new HttpGet("http://localhost:" + port + "/httpclient/back/" + id);
        return getRemoteString(httpclient, httpGet);
    }

    private String getRemoteString(CloseableHttpClient httpclient, HttpGet httpGet) throws IOException {
        String result;
        HttpContext context = new BasicHttpContext();
        CloseableHttpResponse response;
        response = httpclient.execute(httpGet, context);
        try {
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        httpclient.close();
        return result;
    }
}
