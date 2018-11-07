package com.qkun.wanandroid_java.ui.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.bean.HomeBannerBean;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by QKun on 2018/11/5.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, params);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mHomeAdapter = new HomeAdapter(new ArrayList<ArticlesBean.DatasBean>());
        mRecyclerView.setAdapter(mHomeAdapter);

        mPresenter.loadHomeData();

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }


    @Override
    public void getHomeArticles(ArticlesBean articlesBean, int checker) {
        setLoadDataResult(mHomeAdapter, mRefreshLayout, articlesBean.getDatas(), checker);
    }

    @Override
    public void getHomeBanner(List<HomeBannerBean> beans) {

    }


    @Override
    public void showFailed(String message) {

    }
}
