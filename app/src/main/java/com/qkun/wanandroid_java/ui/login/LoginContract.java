package com.qkun.wanandroid_java.ui.login;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.bean.LoginBean;

/**
 * Created by QKun on 2018/11/6.
 */
public interface LoginContract {

    interface View extends BaseContract.BaseIView{
        void LoginSuccess(LoginBean bean);
    }

    interface Presenter extends BaseContract.BaseIPresent<LoginContract.View>{
        //请求登录接口
        void Login(String username, String password);
    }
}
