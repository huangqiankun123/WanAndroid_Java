package com.qkun.wanandroid_java.ui.navigation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by QKun on 2018/11/5.
 */
public class NavigationFragment extends BaseFragment {
    public static final String TAG = "NavigationFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static NavigationFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, params);
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
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
