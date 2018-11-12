package com.qkun.wanandroid_java.ui.search;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.HotKeyBean;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by QKun on 2018/11/12.
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    @Inject
    public SearchPresenter() {
    }

    @Override
    public void loadHotkey() {
        RetrofitManager.createApi(ApiService.class)
                .getHotkey()
                .compose(RxSchedulers.<List<HotKeyBean>>applySchedulers())
                .compose(mView.<List<HotKeyBean>>bindToLife())
                .subscribe(new BaseObserver<List<HotKeyBean>>() {
                    @Override
                    public void _onNext(List<HotKeyBean> beans) {
                        List<String> strings = new ArrayList<>();
                        if (!beans.isEmpty() && beans.size() != 0) {
                            for (HotKeyBean bean : beans) {
                                strings.add(bean.getName());
                            }
                        }
                        mView.getHotkey(strings);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showFailed(msg);
                    }
                });
    }
}
