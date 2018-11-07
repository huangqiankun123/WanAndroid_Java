package com.qkun.wanandroid_java.http;

import com.qkun.wanandroid_java.bean.HomeBannerBean;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.bean.LoginBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by QKun on 2018/11/6.
 */
public interface ApiService {
    /**
     * 登录
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> Login(@Field("username") String username, @Field("password") String password);

    /**
     * 首页文章列表
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticlesBean>> getHomeArticles(@Path("page") int page);

    /**
     * 首页banner
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<HomeBannerBean>>> getHomeBanner();
}
