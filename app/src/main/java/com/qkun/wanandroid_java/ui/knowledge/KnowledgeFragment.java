package com.qkun.wanandroid_java.ui.knowledge;

import android.os.Bundle;
import android.view.View;

import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;

/**
 * Created by QKun on 2018/11/5.
 */
public class KnowledgeFragment extends BaseFragment {
    public static final String TAG = "KnowledgeFragment";

    public static KnowledgeFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, params);
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected void initView(View view) {

    }
}
