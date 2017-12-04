package com.whatsmars.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: qing
 * Date: 14-10-18
 */
public class HttpUtils {

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(6000)
                .setSocketTimeout(6000)
                .build();
        httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .build();
    }

    public static String httpGet(String url) throws Exception {
        return httpGet(url,null);
    }

    public static String httpGet(String url,Map<String,String> params) throws Exception {
        return httpGet(url,params,CHARSET);
    }

    public static String httpGet(String url,Map<String,String> params,String charset) throws Exception {
        if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                Object value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value.toString()));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            if(url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }

        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    public static String httpPost(String url,HttpEntity requestEntity) throws Exception {
        return httpPost(url,null,requestEntity);
    }

    /**
     * 发送body为form格式
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpPost(String url,Map<String,String> params,HttpEntity requestEntity) throws Exception{
        if(params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            if(url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }
        }
        HttpPost httpPost = new HttpPost(url);
        if(requestEntity != null) {
            httpPost.setEntity(requestEntity);
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }


    /**
     * 发送body为输入流
     * sample
     */
    public static String postBodyAsStream(String url,InputStream inputStream, String encoding) throws Exception{
        HttpPost httpPost = new HttpPost(url);
        HttpEntity body = new InputStreamEntity(inputStream);
        httpPost.setEntity(body);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    public static String postBodyAsMultipart(String url,Map<String,ContentBody> contentBodies) throws Exception {
        return postBodyAsMultipart(url,contentBodies,CHARSET);
    }

    /**
     * 发送body为multipart form
     * sample
     */
    public static String postBodyAsMultipart(String url,Map<String,ContentBody> contentBodies,String charset) throws Exception{
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder mb = MultipartEntityBuilder.create();
        mb.setCharset(Charset.forName(charset));
        for(Map.Entry<String,ContentBody> entry : contentBodies.entrySet()) {
            mb.addPart(entry.getKey(),entry.getValue());
        }
        httpPost.setEntity(mb.build());
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    public static InputStream httpGetStream(String url,Map<String,String> params) throws Exception {
        if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                Object value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value.toString()));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            if(url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }

        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null && entity.isStreaming()) {
                return entity.getContent();
            }
            return null;
        } finally {
            //response.close();
        }
    }


    public static String buildUrl(String url, Map<String, String> params, String charset){
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    Object value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value.toString()));
                    }
                }
                String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
                if(url.indexOf("?") > 0) {
                    url += "&" + queryString;
                } else {
                    url += "?" + queryString;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return url;
    }



    public static void main(String[] args) throws Exception {
        String url = "http://stat.whatsmars.com/ds/x2/f00qvewaxsqdnrzazhhyguhduc7wd8sv.png";
        InputStream inputStream = HttpUtils.httpGetStream(url, null);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/javahongxi/Documents/test22222.png"));
            while (true) {
                int i = inputStream.read();
                if( i == -1) {
                    break;
                }
                fileOutputStream.write((byte)i);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }
}
