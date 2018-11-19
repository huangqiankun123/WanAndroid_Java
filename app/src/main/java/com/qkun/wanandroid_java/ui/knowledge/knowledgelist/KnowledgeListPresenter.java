package com.qkun.wanandroid_java.ui.knowledge.knowledgelist;

import android.annotation.SuppressLint;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.CollectStatus;
import com.qkun.wanandroid_java.bean.KnowledgeListBean;
import com.qkun.wanandroid_java.constant.LoadType;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by QKun on 2018/11/19.
 */
public class KnowledgeListPresenter extends BasePresenter<KnowledgeListContract.View> implements KnowledgeListContract.Presenter {

    private int mPage = 0;
    private boolean mRefresh = true;

    @Inject
    public KnowledgeListPresenter() {
        this.mRefresh = true;
    }

    @Override
    public void loadData(int cid) {
        RetrofitManager.createApi(ApiService.class)
                .getKnowledgeList(mPage, cid)
                .compose(RxSchedulers.<KnowledgeListBean>applySchedulers())
                .compose(mView.<KnowledgeListBean>bindToLife())
                .subscribe(new BaseObserver<KnowledgeListBean>() {
                    @Override
                    public void _onNext(KnowledgeListBean bean) {
                        int checker = mRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.getKnowledgeList(bean, checker);
                    }

                    @Override
                    public void _onError(String msg) {
                        int checker = mRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.getKnowledgeList(new KnowledgeListBean(), checker);
                        mView.showFailed(msg);
                    }
                });
    }

    @Override
    public void refresh(int cid) {
        mPage = 0;
        mRefresh = true;
        loadData(cid);
    }

    @Override
    public void loadMore(int cid) {
        mPage++;
        mRefresh = false;
        loadData(cid);
    }

    @SuppressLint("CheckResult")
    @Override
    public void collect(int id, final int position) {
        RetrofitManager.createApi(ApiService.class)
                .collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectStatus>() {
                    @Override
                    public void accept(CollectStatus collectStatus) throws Exception {
                        if (collectStatus.getErrorCode() != 0) {
                            mView.collectFailed(collectStatus.getErrorMsg());
                        } else {
                            mView.collectSuccess(position);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void unCollect(int id, final int position) {
        RetrofitManager.createApi(ApiService.class)
                .unCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectStatus>() {
                    @Override
                    public void accept(CollectStatus collectStatus) throws Exception {
                        if (collectStatus.getErrorCode() != 0) {
                            mView.unCollectFailed(collectStatus.getErrorMsg());
                        } else {
                            mView.unCollectSuccess(position);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });
    }
}
