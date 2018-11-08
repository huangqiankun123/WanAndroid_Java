package com.qkun.wanandroid_java.ui.home;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.bean.HomeBannerBean;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.constant.LoadType;

import java.util.List;

/**
 * Created by QKun on 2018/11/7.
 */
public interface HomeContract {

    interface View extends BaseContract.BaseIView {
        //对View 来说 只关心最终的数据即可
        void getHomeArticles(ArticlesBean articlesBean, @LoadType.checker int checker);

        void getHomeBanner(List<HomeBannerBean> beans);

        void collectSuccess(int position);

        void collectFailed(String msg);

        void unCollectSuccess(int position);

        void unCollectFailed(String msg);

    }

    interface Presenter extends BaseContract.BaseIPresent<HomeContract.View> {
        void loadHomeArticles();

        void loadHomeBanner();

        //banner 和 articles 一起请求
        void loadHomeData();

        void refresh();

        void loadMore();

        //收藏
        void collect(int id,int position);

        //取消收藏
        void unCollect(int id,int position);
    }
}
