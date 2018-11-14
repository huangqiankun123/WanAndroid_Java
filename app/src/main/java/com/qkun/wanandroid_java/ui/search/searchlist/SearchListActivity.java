package com.qkun.wanandroid_java.ui.search.searchlist;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.bean.SearchListBean;
import com.qkun.wanandroid_java.ui.WebActivity;
import com.qkun.wanandroid_java.ui.login.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by QKun on 2018/11/14.
 */
public class SearchListActivity extends BaseActivity<SearchListPresenter> implements SearchListContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private SearchListAdapter mAdapter;
    private String mQuery;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mQuery = getIntent().getStringExtra("query");
        mToolbar.setTitle(mQuery);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(SearchListActivity.this, DividerItemDecoration.VERTICAL));
        mAdapter = new SearchListAdapter(new ArrayList<SearchListBean.DatasBean>());
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_like:
                        boolean collect = mAdapter.getData().get(position).isCollect();
                        int id = mAdapter.getData().get(position).getId();
                        if (collect) {
                            mPresenter.unCollect(id, position);
                        } else {
                            mPresenter.collect(id, position);
                        }
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String link = mAdapter.getData().get(position).getLink();
                Bundle bundle = new Bundle();
                bundle.putString("link", link);
                ActivityUtils.startActivity(bundle, WebActivity.class);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore(mQuery);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.refresh(mQuery);
            }
        });


        mPresenter.queryList(mQuery);

    }


    @Override
    public void querySuccess(SearchListBean bean, int checker) {
        setLoadDataResult(mAdapter, mRefreshLayout, bean.getDatas(), checker);
    }

    @Override
    public void collectSuccess(int position) {
        SearchListBean.DatasBean datasBean = mAdapter.getData().get(position);
        datasBean.setCollect(true);
        mAdapter.notifyItemChanged(position + 1);
        ToastUtils.showShort("收藏成功");
    }

    @Override
    public void collectFailed(String msg) {
        ToastUtils.showShort(msg);
        if (msg.trim().equals("请先登录！")) {
            ActivityUtils.startActivity(LoginActivity.class);
        }
    }

    @Override
    public void unCollectSuccess(int position) {
        SearchListBean.DatasBean datasBean = mAdapter.getData().get(position);
        datasBean.setCollect(false);
        mAdapter.notifyItemChanged(position + 1);
        ToastUtils.showShort("取消收藏成功");
    }

    @Override
    public void unCollectFailed(String msg) {
        ToastUtils.showShort(msg);
        if (msg.trim().equals("请先登录！")) {
            ActivityUtils.startActivity(LoginActivity.class);
        }
    }

    @Override
    public void showFailed(String message) {
        ToastUtils.showShort(message);
    }
}
