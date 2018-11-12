package com.qkun.wanandroid_java.ui.collect;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.bean.CollectBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by QKun on 2018/11/8.
 */
public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private CollectAdapter mCollectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        BarUtils.setStatusBarColor(CollectActivity.this, getResources().getColor(R.color.colorPrimary), 0);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.collect);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_while_24dp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CollectActivity.this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(CollectActivity.this, DividerItemDecoration.VERTICAL));
        mCollectAdapter = new CollectAdapter(new ArrayList<CollectBean.DatasBean>());
        mRecyclerView.setAdapter(mCollectAdapter);

        View empty_view = LayoutInflater.from(CollectActivity.this).inflate(R.layout.recycle_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mCollectAdapter.setEmptyView(empty_view);

        mPresenter.loadCollectList();


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(CollectActivity.this, true);
            }
        });

        mCollectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_collect_like:
                        //直接删除
                        break;
                    default:
                        break;
                }
            }
        });
        mCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("进入详情界面  TODO");
            }
        });

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
    public void getCollectListSuccess(CollectBean collectBean, int checker) {
        setLoadDataResult(mCollectAdapter, mRefreshLayout, collectBean.getDatas(), checker);
    }

}
