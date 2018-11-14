package com.qkun.wanandroid_java.ui.search.searchlist;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.bean.SearchListBean;
import com.qkun.wanandroid_java.constant.LoadType;

/**
 * Created by QKun on 2018/11/14.
 */
public interface SearchListContract {

    interface View extends BaseContract.BaseIView {

        void querySuccess(SearchListBean bean, @LoadType.checker int checker);

        void collectSuccess(int position);

        void collectFailed(String msg);

        void unCollectSuccess(int position);

        void unCollectFailed(String msg);

    }

    interface Presenter extends BaseContract.BaseIPresent<SearchListContract.View> {

        void queryList(String query);

        void refresh(String query);

        void loadMore(String query);

        //收藏
        void collect(int id,int position);

        //取消收藏
        void unCollect(int id,int position);
    }
}
