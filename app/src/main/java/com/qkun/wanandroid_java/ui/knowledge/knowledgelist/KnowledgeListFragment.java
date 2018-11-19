package com.qkun.wanandroid_java.ui.knowledge.knowledgelist;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.bean.KnowledgeListBean;
import com.qkun.wanandroid_java.ui.WebActivity;
import com.qkun.wanandroid_java.ui.login.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by QKun on 2018/11/16.
 */
public class KnowledgeListFragment extends BaseFragment<KnowledgeListPresenter> implements KnowledgeListContract.View {

    private static final String TAG = "KnowledgeListFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private KnowledgeListAdapter mAdapter;

    public static KnowledgeListFragment newInstance(int cid) {
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, cid);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_list;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected void initView(View view) {
        final int cid = getArguments().getInt(TAG);
        ToastUtils.showShort("id-->" + cid);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new KnowledgeListAdapter(new ArrayList<KnowledgeListBean.DatasBean>());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String link = mAdapter.getData().get(position).getLink();
                Bundle bundle = new Bundle();
                bundle.putString("link", link);
                ActivityUtils.startActivity(bundle,WebActivity.class);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_like:
                        KnowledgeListBean.DatasBean datasBean = mAdapter.getData().get(position);
                        if (datasBean.isCollect()) {
                            mPresenter.unCollect(datasBean.getId(), position);
                        } else {
                            mPresenter.collect(datasBean.getId(), position);
                        }

                        break;
                    default:
                        break;
                }
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore(cid);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.refresh(cid);
            }
        });

        mPresenter.loadData(cid);

    }

    @Override
    public void getKnowledgeList(KnowledgeListBean bean, int checker) {
        setLoadDataResult(mAdapter, mRefreshLayout, bean.getDatas(), checker);
    }

    @Override
    public void collectSuccess(int position) {
        KnowledgeListBean.DatasBean datasBean = mAdapter.getData().get(position);
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
        KnowledgeListBean.DatasBean datasBean = mAdapter.getData().get(position);
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
