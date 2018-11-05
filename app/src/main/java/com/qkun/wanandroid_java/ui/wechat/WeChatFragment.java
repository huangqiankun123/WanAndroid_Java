package com.qkun.wanandroid_java.ui.wechat;

import android.os.Bundle;
import android.view.View;

import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.ui.home.HomeFragment;

/**
 * Created by QKun on 2018/11/5.
 */
public class WeChatFragment extends BaseFragment {
    public static final String TAG = "WeChatFragment";

    public static WeChatFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, params);
        WeChatFragment fragment = new WeChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
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
