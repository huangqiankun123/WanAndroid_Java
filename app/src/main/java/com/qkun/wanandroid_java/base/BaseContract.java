package com.qkun.wanandroid_java.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by QKun on 2018/10/31.
 * base契约类  基本操作
 */
public class BaseContract {

    //present 与 吸附  和  分离, rxLife 同效果（研究下为什么还需要rxLife）
    public interface BaseIPresent<T extends BaseContract.BaseIView> {

        void attachView(T view);

        void detachView();
    }

    //view 的基本显示
    public interface BaseIView {
        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFaild(String message);

        //显示当前网络不可用
        void showNoNet();

        //重试
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

        /**
         * 没有数据
         */
        void NoData();
    }
}
