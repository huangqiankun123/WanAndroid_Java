package com.qkun.wanandroid_java.ui.knowledge;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.KnowledgeTreeBean;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by QKun on 2018/11/15.
 */
public class KnowledgePresneter extends BasePresenter<KnowledgeContract.View> implements KnowledgeContract.Presenter {

    @Inject
    public KnowledgePresneter() {
    }

    @Override
    public void loadKnowledgeTree() {
        RetrofitManager.createApi(ApiService.class)
                .getKnowledgeTree()
                .compose(RxSchedulers.<List<KnowledgeTreeBean>>applySchedulers())
                .compose(mView.<List<KnowledgeTreeBean>>bindToLife())
                .subscribe(new BaseObserver<List<KnowledgeTreeBean>>() {
                    @Override
                    public void _onNext(List<KnowledgeTreeBean> treeBeans) {
                        mView.getKnowledgeTreeSuccess(treeBeans);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showFailed(msg);
                    }
                });
    }


}
