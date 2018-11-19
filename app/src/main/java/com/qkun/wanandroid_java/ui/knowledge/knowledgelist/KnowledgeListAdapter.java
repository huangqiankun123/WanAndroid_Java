package com.qkun.wanandroid_java.ui.knowledge.knowledgelist;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.bean.KnowledgeListBean;
import com.qkun.wanandroid_java.utils.GlideUtils;

import java.util.List;

/**
 * Created by QKun on 2018/11/19.
 */
public class KnowledgeListAdapter extends BaseQuickAdapter<KnowledgeListBean.DatasBean,BaseViewHolder> {
    public KnowledgeListAdapter(@Nullable List<KnowledgeListBean.DatasBean> data) {
        super(R.layout.knowledge_list_recycle_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeListBean.DatasBean item) {

        helper.setText(R.id.tv_article_author, item.getAuthor());
        helper.setText(R.id.tv_article_date, item.getNiceDate());
        TextView tv_article_new = helper.getView(R.id.tv_article_new);
        if (item.getNiceDate().contains("小时")){
            tv_article_new.setVisibility(View.VISIBLE);
        }else {
            tv_article_new.setVisibility(View.GONE);
        }
        ImageView iv_article_thumbnail = helper.getView(R.id.iv_article_thumbnail);
        if (!item.getEnvelopePic().isEmpty()) {
            iv_article_thumbnail.setVisibility(View.VISIBLE);
            GlideUtils.load(mContext, item.getEnvelopePic(), iv_article_thumbnail);
        } else {
            iv_article_thumbnail.setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_article_title, item.getTitle());
        helper.setText(R.id.tv_article_chapterName, item.getSuperChapterName() + "/" + item.getChapterName());

        boolean collect = item.isCollect();
        if (collect) {
            helper.setImageResource(R.id.iv_like, R.drawable.ic_like);
        } else {
            helper.setImageResource(R.id.iv_like, R.drawable.ic_like_not);
        }

        helper.addOnClickListener(R.id.iv_like);
    }
}
