package com.qkun.wanandroid_java.ui.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.db.HistoryModel;

import java.util.List;

/**
 * Created by QKun on 2018/11/12.
 */
public class HistoryAdapter extends BaseQuickAdapter<HistoryModel, BaseViewHolder> {
    public HistoryAdapter(@Nullable List<HistoryModel> data) {
        super(R.layout.history_recycle_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HistoryModel item) {
        helper.setText(R.id.tv_history_name, item.getName());
        helper.addOnClickListener(R.id.iv_clear);
    }
}
