package com.qkun.wanandroid_java.ui.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.bean.HomeBannerBean;
import com.qkun.wanandroid_java.bean.ArticlesBean;
import com.qkun.wanandroid_java.ui.login.LoginActivity;
import com.qkun.wanandroid_java.utils.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

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
    private Banner mBanner;

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

        View home_recycle_header = LayoutInflater.from(getActivity()).inflate(R.layout.home_recycle_header, (ViewGroup) mRecyclerView.getParent(), false);
        mBanner = home_recycle_header.findViewById(R.id.banner);
        mHomeAdapter.addHeaderView(home_recycle_header);

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

        mHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("点击条目事件触发 TODO");
            }
        });
        mHomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_like:
                        ArticlesBean.DatasBean datasBean = mHomeAdapter.getData().get(position);
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
    }


    @Override
    public void getHomeArticles(ArticlesBean articlesBean, int checker) {
        setLoadDataResult(mHomeAdapter, mRefreshLayout, articlesBean.getDatas(), checker);
    }

    @Override
    public void getHomeBanner(final List<HomeBannerBean> beans) {
        List<String> images = new ArrayList();
        List<String> titles = new ArrayList();
        for (HomeBannerBean bean : beans) {
            images.add(bean.getImagePath());
            titles.add(bean.getTitle());
        }
        mBanner.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.showLong(beans.get(position).getUrl());
            }
        });
    }

    @Override
    public void collectSuccess(int position) {
        ArticlesBean.DatasBean datasBean = mHomeAdapter.getData().get(position);
        datasBean.setCollect(true);
        mHomeAdapter.notifyItemChanged(position + 1);
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
        ArticlesBean.DatasBean datasBean = mHomeAdapter.getData().get(position);
        datasBean.setCollect(false);
        mHomeAdapter.notifyItemChanged(position + 1);
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

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }
}
