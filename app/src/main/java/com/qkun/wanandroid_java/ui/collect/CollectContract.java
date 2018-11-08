package com.qkun.wanandroid_java.ui.collect;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.bean.CollectBean;
import com.qkun.wanandroid_java.constant.LoadType;

/**
 * Created by QKun on 2018/11/8.
 */
public interface CollectContract {

    interface View extends BaseContract.BaseIView {

        void getCollectListSuccess(CollectBean collectBean,@LoadType.checker int checker);
    }

    interface Presenter extends BaseContract.BaseIPresent<CollectContract.View> {

        void loadCollectList();

        void refresh();

        void loadMore();
    }
}
