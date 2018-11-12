package com.qkun.wanandroid_java.ui.search;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;


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

        mPresenter.loadHotkey();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.onActionViewExpanded();
        searchView.setQueryHint(getString(R.string.search_tint));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        try {
            Field field = searchView.getClass().getDeclaredField("mGoButton");
            field.setAccessible(true);
            ImageView iv = (ImageView) field.get(searchView);
            iv.setImageResource(R.drawable.ic_search_white_24dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ToastUtils.showShort(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ToastUtils.showShort(newText);
        return true;
    }


    @Override
    public void getHotkey(final List<String> strings) {
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
                ToastUtils.showShort(strings.get(position));
                return true;
            }
        });

    }

}
