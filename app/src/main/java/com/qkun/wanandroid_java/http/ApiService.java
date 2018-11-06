package com.qkun.wanandroid_java.http;

import com.qkun.wanandroid_java.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by QKun on 2018/11/6.
 */
public interface ApiService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> Login(@Field("username") String username, @Field("password") String password);

}
