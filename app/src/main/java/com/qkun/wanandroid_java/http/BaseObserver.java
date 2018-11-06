package com.qkun.wanandroid_java.http;


import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by QKun on 2018/11/6.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (!NetworkUtils.isConnected()){
            ToastUtils.showShort("网络不可用");
        }else if (e instanceof ApiService){
            //token 失效 进入登录页面 等一些列错误处理
        }else if (e instanceof ConnectException){
            ToastUtils.showShort("请求超时，请稍后再试...");
        }else if (e instanceof SocketTimeoutException){
            ToastUtils.showShort("服务器响应超时，请稍后再试...");
        }else {
            ToastUtils.showShort("请求超时，请稍后再试...");
        }

    }

    @Override
    public void onComplete() {

    }
}
