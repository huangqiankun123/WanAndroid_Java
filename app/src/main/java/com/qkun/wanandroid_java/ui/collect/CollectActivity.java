package com.qkun.wanandroid_java.ui.collect;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.bean.CollectBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QKun on 2018/11/8.
 */
public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

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

    }

    @Override
    public void getCollectListSuccess(CollectBean collectBean, int checker) {
//        setLoadDataResult();
    }

}
