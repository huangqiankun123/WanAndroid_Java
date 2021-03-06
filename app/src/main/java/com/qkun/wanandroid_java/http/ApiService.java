package com.qkun.wanandroid_java.http;

import com.qkun.wanandroid_java.bean.CollectBean;
import com.qkun.wanandroid_java.bean.CollectStatus;
import com.qkun.wanandroid_java.bean.HomeBannerBean;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.bean.HotKeyBean;
import com.qkun.wanandroid_java.bean.KnowledgeListBean;
import com.qkun.wanandroid_java.bean.KnowledgeTreeBean;
import com.qkun.wanandroid_java.bean.LoginBean;
import com.qkun.wanandroid_java.bean.SearchListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by QKun on 2018/11/6.
 */
public interface ApiService {
    /**
     * 登录
     *
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> Login(@Field("username") String username, @Field("password") String password);

    /**
     * 退出APP
     *
     * @return
     */
    @GET("user/logout/json")
    Observable<BaseResponse<Object>> logout();

    /**
     * 首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticlesBean>> getHomeArticles(@Path("page") int page);

    /**
     * 1.2 首页banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<HomeBannerBean>>> getHomeBanner();


    /**
     * 1.4 搜索热词
     *
     * @return
     */
    @GET("hotkey/json")
    Observable<BaseResponse<List<HotKeyBean>>> getHotkey();


    /**
     * 2.1 体系数据
     *
     * @return
     */
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeTreeBean>>> getKnowledgeTree();

    /**
     * 2.2 知识体系下的文章
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<KnowledgeListBean>> getKnowledgeList(@Path("page") int page, @Query("cid") int cid);


    /**
     * 6.1 收藏文章列表
     *
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<CollectBean>> getCollectList(@Path("page") int page);

    /**
     * 6.2 收藏站内文章
     *
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<CollectStatus> collect(@Path("id") int id);

    /**
     * 取消收藏
     * 6.4.1 文章列表
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<CollectStatus> unCollect(@Path("id") int id);

    /**
     * 7.1 搜索
     *
     * @param page  页数
     * @param query 关键字
     * @return
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResponse<SearchListBean>> search(@Path("page") int page, @Field("k") String query);
}
