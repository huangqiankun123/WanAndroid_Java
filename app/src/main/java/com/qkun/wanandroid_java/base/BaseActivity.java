package com.qkun.wanandroid_java.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qkun.wanandroid_java.constant.Constant;
import com.qkun.wanandroid_java.constant.LoadType;
import com.qkun.wanandroid_java.di.component.ActivityComponent;
import com.qkun.wanandroid_java.di.component.DaggerActivityComponent;
import com.qkun.wanandroid_java.di.mudule.ActivityModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.List;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by QKun on 2018/10/31.
 */
public abstract class BaseActivity<T extends BaseContract.BaseIPresent> extends RxAppCompatActivity implements BaseContract.BaseIView ,View.OnClickListener {
    @Nullable
    @Inject
    protected T mPresenter;

    protected ActivityComponent mActivityComponent;

    private Unbinder mBind;

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 进行dagger注入
     */
    protected abstract void initInjector();

    /**
     * 初始化布局
     */
    protected abstract void initView();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        setContentView(getLayoutId());
        initInjector();
        mBind = ButterKnife.bind(this);
        attachView();
        initView();
    }

    /**
     * present 吸附 view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * presenter 分离 view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 初始化ActivityComponent
     * 通过application 获取的applicationComponent 再根据依赖关系获取mActivityComponent 获取的依赖对象是同一个
     */
    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        detachView();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void NoData() {

    }


    protected void setLoadDataResult(BaseQuickAdapter adapter, SmartRefreshLayout refreshLayout, List list,
                                     @LoadType.checker int loadType) {
        switch (loadType) {
            case LoadType.TYPE_REFRESH_SUCCESS:
                adapter.setNewData(list);
                refreshLayout.finishRefresh();
                break;
            case LoadType.TYPE_REFRESH_ERROR:
                refreshLayout.finishRefresh();
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if (list != null) {
                    adapter.addData(list);
                    refreshLayout.finishLoadMore();
                }
                break;
            case LoadType.TYPE_LOAD_MORE_ERROR:
                break;
            default:
                break;
        }
        if (list == null || list.isEmpty() || list.size() < Constant.PAGE_SIZE) {
            adapter.loadMoreEnd(false);
        } else {
            adapter.loadMoreComplete();
        }
    }
}
