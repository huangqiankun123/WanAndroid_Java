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

    }

    interface Presenter extends BaseContract.BaseIPresent<HomeContract.View> {
        void loadHomeArticles();

        void loadHomeBanner();

        //banner 和 articles 一起请求
        void loadHomeData();

        void refresh();

        void loadMore();
    }
}
