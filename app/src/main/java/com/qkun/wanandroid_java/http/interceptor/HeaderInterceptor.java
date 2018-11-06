package com.qkun.wanandroid_java.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by QKun on 2018/11/6.
 * 设置请求头
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-type", "application/json; charset=utf-8");
        return chain.proceed(builder.build());
    }
}
