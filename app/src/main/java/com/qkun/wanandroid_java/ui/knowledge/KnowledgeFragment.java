package com.qkun.wanandroid_java.ui.knowledge;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.bean.KnowledgeTreeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by QKun on 2018/11/5.
 */
public class KnowledgeFragment extends BaseFragment<KnowledgePresneter> implements KnowledgeContract.View {
    public static final String TAG = "KnowledgeFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private KnowledgeAdapter mAdapter;

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
        mFragmentComponent.inject(this);
    }

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mAdapter = new KnowledgeAdapter(new ArrayList<KnowledgeTreeBean>());
        mRecyclerView.setAdapter(mAdapter);
        View recycle_empty = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(recycle_empty);

        mPresenter.loadKnowledgeTree();
    }

    @Override
    public void getKnowledgeTreeSuccess(List<KnowledgeTreeBean> treeBeans) {
        mAdapter.setNewData(treeBeans);
    }
}
