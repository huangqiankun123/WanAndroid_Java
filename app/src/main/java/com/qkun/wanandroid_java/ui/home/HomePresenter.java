package com.qkun.wanandroid_java.ui.home;

import android.annotation.SuppressLint;

import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.HomeBannerBean;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.constant.Constant;
import com.qkun.wanandroid_java.constant.LoadType;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.BaseResponse;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by QKun on 2018/11/7.
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private int mPage = 0;
    private boolean mIsRefresh;

    @Inject
    public HomePresenter() {
        this.mIsRefresh = true;
    }

    @Override
    public void loadHomeArticles() {
        RetrofitManager.createApi(ApiService.class)
                .getHomeArticles(mPage)
                .compose(RxSchedulers.<ArticlesBean>applySchedulers())
                .compose(mView.<ArticlesBean>bindToLife())
                .subscribe(new BaseObserver<ArticlesBean>() {
                    @Override
                    public void _onNext(ArticlesBean articlesBean) {
                        int checker = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.getHomeArticles(articlesBean, checker);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showFailed(msg);
                    }
                });

    }

    @Override
    public void loadHomeBanner() {
        RetrofitManager.createApi(ApiService.class)
                .getHomeBanner()
                .compose(RxSchedulers.<List<HomeBannerBean>>applySchedulers())
                .compose(mView.<List<HomeBannerBean>>bindToLife())
                .subscribe(new BaseObserver<List<HomeBannerBean>>() {
                    @Override
                    public void _onNext(List<HomeBannerBean> beans) {

                        mView.getHomeBanner(beans);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showFailed(msg);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadHomeData() {
        Observable<BaseResponse<List<HomeBannerBean>>> homeBanner = RetrofitManager.createApi(ApiService.class).getHomeBanner();
        Observable<BaseResponse<ArticlesBean>> homeArticles = RetrofitManager.createApi(ApiService.class).getHomeArticles(mPage);
        Observable.zip(homeArticles, homeBanner, new BiFunction<BaseResponse<ArticlesBean>, BaseResponse<List<HomeBannerBean>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(BaseResponse<ArticlesBean> homeBeanBaseResponse, BaseResponse<List<HomeBannerBean>> listBaseResponse) throws Exception {
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put(Constant.BANNER_KEY, listBaseResponse.getData());
                objectMap.put(Constant.ARTICLE_KEY, homeBeanBaseResponse.getData());
                return objectMap;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<Map<String, Object>>bindToLife())
                .subscribe(new Consumer<Map<String, Object>>() {
                    @Override
                    public void accept(Map<String, Object> map) throws Exception {
                        List<HomeBannerBean> bannerBeans = (List<HomeBannerBean>) map.get(Constant.BANNER_KEY);
                        ArticlesBean articlesBeans = (ArticlesBean) map.get(Constant.ARTICLE_KEY);
                        mView.getHomeBanner(bannerBeans);
                        mView.getHomeArticles(articlesBeans, LoadType.TYPE_REFRESH_SUCCESS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });


    }

    @Override
    public void refresh() {
        mIsRefresh = true;
        mPage = 0;
//        loadHomeBanner();
        loadHomeArticles();

    }

    @Override
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadHomeArticles();
    }

}
