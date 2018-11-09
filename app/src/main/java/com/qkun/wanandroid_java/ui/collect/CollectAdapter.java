package com.qkun.wanandroid_java.ui.collect;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.bean.CollectBean;
import com.qkun.wanandroid_java.utils.GlideUtils;

import java.util.List;

/**
 * Created by QKun on 2018/11/9.
 */
public class CollectAdapter extends BaseQuickAdapter<CollectBean.DatasBean, BaseViewHolder> {
    public CollectAdapter(@Nullable List<CollectBean.DatasBean> data) {
        super(R.layout.collect_recycle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectBean.DatasBean item) {
        helper.setText(R.id.tv_collect_author, item.getAuthor());
        helper.setText(R.id.tv_collect_date, item.getNiceDate());
        AppCompatImageView iv_collect_thumbnail = helper.getView(R.id.iv_collect_thumbnail);
        String envelopePic = item.getEnvelopePic();
        if (!TextUtils.isEmpty(envelopePic)) {
            iv_collect_thumbnail.setVisibility(View.VISIBLE);
            GlideUtils.load(mContext, envelopePic, iv_collect_thumbnail);
        } else {
            iv_collect_thumbnail.setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_collect_title,item.getTitle());
        helper.setText(R.id.tv_collect_chapterName,item.getChapterName());

        if (item.isCollect()) {
            helper.setImageResource(R.id.iv_collect_like, R.drawable.ic_like);
        } else {
            helper.setImageResource(R.id.iv_collect_like, R.drawable.ic_like_not);
        }

        helper.addOnClickListener(R.id.iv_collect_like);

    }
}
