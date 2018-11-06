package com.qkun.wanandroid_java.http.cookies;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by QKun on 2018/4/25.
 */

public class CookiesManager implements CookieJar {

    private static final PersistentCookieStore cookieStore = new PersistentCookieStore();

    /**
     * 保存了下来。cookie
     * 读取response header中的cookie
     *
     * @param url
     * @param cookies
     */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        LogUtils.d("响应：嘤嘤嘤");
        if (cookies != null && cookies.size() > 0) {
            for (Cookie cookie : cookies) {
                cookieStore.add(url, cookie);
            }
        } else {
//            ActivityUtils.startActivity(LoginActivity.class);
        }
    }

    /**
     * 帮助我们从请求头里面获取了cookie
     * 请求时在发送时向request header中加入cookie
     *
     * @param url
     * @return
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        LogUtils.d("请求：嘤嘤嘤");
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

    /**
     * 清除所有cookie
     */
    public static void clearAllCookies() {
        cookieStore.removeAll();
    }

    /**
     * 清除指定cookie
     *
     * @param url
     * @param cookie
     * @return
     */
    public static boolean clearCookies(HttpUrl url, Cookie cookie) {
        return cookieStore.remove(url, cookie);
    }

    /**
     * 获取cookies
     *
     * @return
     */
    public static List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }
}
