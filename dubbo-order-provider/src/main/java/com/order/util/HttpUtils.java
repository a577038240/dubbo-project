package com.order.util;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author :
 * @date 2020-12-31 10:38
 * @Since V1.0.0
 * @description
 */

public abstract class HttpUtils {
    private static final OkHttpClient OK_HTTP_CLIENT;
    private static final transient Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static final MediaType JSON_CONTENT_TYPE = MediaType.parse("application/json;charset=utf-8");

    static {
        OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .hostnameVerifier((s, session) -> true)
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .build();
    }

    public static String postForm(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (Objects.nonNull(params) && !params.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                log.info("call {} by form,param = {}, SUCCESS. this response :{} ", url, params, responseStr);
                return responseStr;
            }
            throw new RuntimeException(response.message());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("call {} by form,param = {}, failed. this reason :{} ", url, params, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String postFormObj(String url, Object param) {
        Map<String, String> params = new HashMap<>();
        if (Objects.nonNull(param)) {
            String jsonStr = JsonUtil.toJsonStr(param);
            params = JsonUtil.parseObject(jsonStr, new TypeReference<Map<String, String>>() {});
        }
        return postForm(url, params);
    }

    public static String postJson(String url, String jsonParam) {
        RequestBody requestBody = RequestBody.create(jsonParam, JSON_CONTENT_TYPE);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                log.info("call {} by json,param = {}, SUCCESS. this response :{} ", url, jsonParam, responseStr);
                return responseStr;
            }
            throw new RuntimeException(response.message());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("call {} by json,param = {}, failed. this reason :{} ", url, jsonParam, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String get(String url, String jsonParam) {
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (StringUtils.isNotBlank(jsonParam)) {
            Map<String, String> paramMaps = JsonUtil.parseObject(jsonParam, new TypeReference<Map<String, String>>() {
            });
            Set<Map.Entry<String, String>> entries = paramMaps.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new RuntimeException(response.message());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
