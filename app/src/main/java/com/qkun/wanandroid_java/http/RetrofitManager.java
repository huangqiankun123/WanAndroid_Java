package com.qkun.wanandroid_java.http;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.qkun.wanandroid_java.constant.Constant;
import com.qkun.wanandroid_java.http.cookies.CookiesManager;
import com.qkun.wanandroid_java.http.interceptor.CacheInterceptor;
import com.qkun.wanandroid_java.http.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by QKun on 2018/11/6.
 */
public class RetrofitManager {

    private static final String TAG = "wanAndroid";
    private volatile static Retrofit instance = null;

    private static long CONNECT_TIMEOUT = 60L;
    private static long READ_TIMEOUT = 10L;
    private static long WRITE_TIMEOUT = 10L;

    private static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .client(getOkHttpClient())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return instance;
    }

    /**
     * 创建Retrofit请求Api
     *
     * @param clazz Retrofit Api接口
     * @return api实例
     */
    public static <T> T createApi(Class<T> clazz) {
        return getInstance().create(clazz);
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                //.addInterceptor(new CacheInterceptor())
                .cookieJar(new CookiesManager())
                .build();

    }

    private static HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d(TAG, message);
            }
        }).setLevel(AppUtils.isAppDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

}
