package com.qkun.wanandroid_java.ui.login;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.LoginBean;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import javax.inject.Inject;

/**
 * Created by QKun on 2018/11/6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void Login(String username, String password) {
        RetrofitManager.createApi(ApiService.class)
                .Login(username, password)
                .compose(RxSchedulers.<LoginBean>applySchedulers())
                .compose(mView.<LoginBean>bindToLife())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    public void _onNext(LoginBean loginBean) {
                        mView.LoginSuccess(loginBean);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showFaild(msg);
                    }
                });
    }
}
