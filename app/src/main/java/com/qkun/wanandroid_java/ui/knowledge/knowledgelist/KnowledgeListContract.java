package com.qkun.wanandroid_java.ui.knowledge.knowledgelist;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.bean.KnowledgeListBean;
import com.qkun.wanandroid_java.constant.LoadType;

/**
 * Created by QKun on 2018/11/19.
 */
public interface KnowledgeListContract {

    interface View extends BaseContract.BaseIView {
        void getKnowledgeList(KnowledgeListBean bean, @LoadType.checker int checker);

        void collectSuccess(int position);

        void collectFailed(String msg);

        void unCollectSuccess(int position);

        void unCollectFailed(String msg);
    }

    interface Presenter extends BaseContract.BaseIPresent<KnowledgeListContract.View> {
        void loadData(int cid);

        void refresh(int cid);

        void loadMore(int cid);

        //收藏
        void collect(int id,int position);

        //取消收藏
        void unCollect(int id,int position);
    }
}
