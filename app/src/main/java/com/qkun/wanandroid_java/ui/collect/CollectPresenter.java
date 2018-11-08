package com.qkun.wanandroid_java.ui.collect;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.CollectBean;
import com.qkun.wanandroid_java.constant.LoadType;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import javax.inject.Inject;

/**
 * Created by QKun on 2018/11/8.
 */
public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {
    private int mPage = 0;
    private boolean mIsRefresh;

    @Inject
    public CollectPresenter() {
        this.mIsRefresh = true;
    }

    @Override
    public void loadCollectList() {
        RetrofitManager.createApi(ApiService.class)
                .getCollectList(mPage)
                .compose(RxSchedulers.<CollectBean>applySchedulers())
                .compose(mView.<CollectBean>bindToLife())
                .subscribe(new BaseObserver<CollectBean>() {
                    @Override
                    public void _onNext(CollectBean collectBean) {
                        int checker = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.getCollectListSuccess(collectBean, checker);
                    }

                    @Override
                    public void _onError(String msg) {
                        int checker = mIsRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.getCollectListSuccess(new CollectBean(), checker);
                        mView.showFailed(msg);
                    }
                });
    }

    @Override
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadCollectList();
    }

    @Override
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadCollectList();
    }

}
