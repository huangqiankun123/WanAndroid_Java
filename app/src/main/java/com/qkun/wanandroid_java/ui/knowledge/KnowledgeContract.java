package com.qkun.wanandroid_java.ui.knowledge;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.bean.KnowledgeTreeBean;

import java.util.List;

/**
 * Created by QKun on 2018/11/15.
 */
public interface KnowledgeContract {

    interface View extends BaseContract.BaseIView {

        void getKnowledgeTreeSuccess(List<KnowledgeTreeBean> treeBeans);
    }

    interface Presenter extends BaseContract.BaseIPresent<KnowledgeContract.View> {

        void loadKnowledgeTree();
    }
}
