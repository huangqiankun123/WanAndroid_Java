package com.qkun.wanandroid_java.ui.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by QKun on 2018/11/12.
 */
public class HistoryAdapter extends BaseQuickAdapter<Object,BaseViewHolder> {
    public HistoryAdapter(@Nullable List<Object> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
