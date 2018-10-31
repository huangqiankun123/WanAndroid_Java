package com.qkun.wanandroid_java.base;

/**
 * Created by QKun on 2018/10/31.
 * mvp 思想:present 持有IBaseView   view 持有BaseIPresent
 * View与Presenter之间的通信则需要通过接口来完成，这样就将视图层与逻辑层进行了分离，也就是解耦。
 */
public class BasePresenter<T extends BaseContract.BaseIView> implements BaseContract.BaseIPresent<T> {

    protected T mView;

    @Override
    public void attachView(BaseContract.BaseIView view) {
        this.mView = (T) view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
