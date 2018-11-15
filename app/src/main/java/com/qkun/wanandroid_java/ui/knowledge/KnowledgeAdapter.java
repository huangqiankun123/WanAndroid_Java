package com.qkun.wanandroid_java.ui.knowledge;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.bean.KnowledgeTreeBean;

import java.util.List;

/**
 * Created by QKun on 2018/11/15.
 */
public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeTreeBean, BaseViewHolder> {
    public KnowledgeAdapter(@Nullable List<KnowledgeTreeBean> data) {
        super(R.layout.knowledge_recycle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeTreeBean item) {
        StringBuffer buffer = new StringBuffer();
        helper.setText(R.id.title_first, item.getName());
        List<KnowledgeTreeBean.ChildrenBean> children = item.getChildren();
        for (KnowledgeTreeBean.ChildrenBean child : children) {
            buffer.append(child.getName() + "　");
        }
        helper.setText(R.id.title_second, buffer.toString());
    }
}
