package com.qkun.wanandroid_java.ui.search.searchlist;

import android.annotation.SuppressLint;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.CollectStatus;
import com.qkun.wanandroid_java.bean.SearchListBean;
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
 * Created by QKun on 2018/11/14.
 */
public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter {

    private int mPage = 0;
    private boolean mRefresh = true;

    @Inject
    public SearchListPresenter() {
        this.mRefresh = true;
    }

    @Override
    public void queryList(String query) {
        RetrofitManager.createApi(ApiService.class)
                .search(mPage, query)
                .compose(RxSchedulers.<SearchListBean>applySchedulers())
                .compose(mView.<SearchListBean>bindToLife())
                .subscribe(new BaseObserver<SearchListBean>() {
                    @Override
                    public void _onNext(SearchListBean searchListBean) {
                        int checker = mRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.querySuccess(searchListBean, checker);
                    }

                    @Override
                    public void _onError(String msg) {
                        int checker = mRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.querySuccess(new SearchListBean(), checker);
                        mView.showFailed(msg);
                    }
                });
    }

    @Override
    public void refresh(String query) {
        mPage = 0;
        mRefresh = true;
        queryList(query);
    }

    @Override
    public void loadMore(String query) {
        mPage++;
        mRefresh = false;
        queryList(query);
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
