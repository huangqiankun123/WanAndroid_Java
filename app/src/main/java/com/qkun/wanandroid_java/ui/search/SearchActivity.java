package com.qkun.wanandroid_java.ui.search;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.db.DataManager;
import com.qkun.wanandroid_java.db.HistoryModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by QKun on 2018/11/5.
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView.OnQueryTextListener, SearchContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.hot_flowlayout)
    TagFlowLayout mHotFlowlayout;
    @BindView(R.id.tv_search_clear)
    TextView mTvSearchClear;
    @BindView(R.id.rv_history_search)
    RecyclerView mRvHistorySearch;
    private List<HistoryModel> historys = new ArrayList<>();
    private HistoryAdapter mHistoryAdapter;
    private SearchView mSearchView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRvHistorySearch.setLayoutManager(new LinearLayoutManager(this));
        mRvHistorySearch.addItemDecoration(new DividerItemDecoration(SearchActivity.this, DividerItemDecoration.VERTICAL));
        mHistoryAdapter = new HistoryAdapter(new ArrayList<HistoryModel>());
        mRvHistorySearch.setAdapter(mHistoryAdapter);
        View search_recycle_empty = LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_recycle_empty, (ViewGroup) mRvHistorySearch.getParent(), false);
        mHistoryAdapter.setEmptyView(search_recycle_empty);

        mHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_clear:
                        mHistoryAdapter.getData().remove(position);
                        mHistoryAdapter.notifyItemRemoved(position);

                        mPresenter.deleteData(position);

                        break;
                }
            }
        });

        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = mHistoryAdapter.getData().get(position).getName();
                mSearchView.setQuery(name, true);
            }
        });

        mPresenter.loadHotkey();
    }

    @OnClick({R.id.tv_search_clear})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search_clear:
                mHistoryAdapter.getData().clear();
                mHistoryAdapter.notifyDataSetChanged();
                mPresenter.deleteDataAll();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.onActionViewExpanded();
        mSearchView.setQueryHint(getString(R.string.search_tint));
        // 设置该SearchView显示确认搜索按钮
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);

        try {
            Field field = mSearchView.getClass().getDeclaredField("mGoButton");
            field.setAccessible(true);
            ImageView iv = (ImageView) field.get(mSearchView);
            iv.setImageResource(R.drawable.ic_search_white_24dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //先添加到数据库中 再跳转
        mPresenter.addHistory(query);
        ToastUtils.showShort(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }


    @Override
    public void getHotkey(final List<String> strings, List<HistoryModel> modelList) {
        mHotFlowlayout.setAdapter(new TagAdapter(strings) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView hot_item = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.hot_item, parent, false);
                hot_item.setText(o.toString());
                return hot_item;
            }
        });
        mHotFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mSearchView.setQuery(strings.get(position), true);
                return true;
            }
        });
        historys = modelList;
        mHistoryAdapter.setNewData(historys);

    }

    @Override
    public void addHistorySuccess(HistoryModel historyModel) {
        if (historys.contains(historyModel)) {
            int i = historys.indexOf(historyModel);
            historys.add(0, historys.remove(i));
        } else {
            historys.add(0, historyModel);
        }
        mHistoryAdapter.setNewData(historys);
    }

}
