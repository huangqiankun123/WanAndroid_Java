package com.qkun.wanandroid_java.ui.knowledge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.bean.KnowledgeTreeBean;
import com.qkun.wanandroid_java.ui.MainActivity;
import com.qkun.wanandroid_java.ui.knowledge.knowledgelist.KnowledgeListFragment;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by QKun on 2018/11/16.
 */
public class KnowledgeActivity extends BaseActivity implements TabLayout.BaseOnTabSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_page)
    ViewPager mViewPage;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private List<KnowledgeTreeBean.ChildrenBean> mChildren;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        BarUtils.setStatusBarColor(KnowledgeActivity.this, getResources().getColor(R.color.colorPrimary), 0);

        Bundle extras = getIntent().getExtras();
        KnowledgeTreeBean treeBean = (KnowledgeTreeBean) extras.getSerializable("bean");
        if (null != treeBean) {
            mToolbar.setTitle(treeBean.getName());
            mChildren = treeBean.getChildren();
            for (KnowledgeTreeBean.ChildrenBean child : mChildren) {
                KnowledgeListFragment fragment = KnowledgeListFragment.newInstance(child.getId());
                mFragments.add(fragment);
            }
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTabLayout.addOnTabSelectedListener(this);
        mViewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mChildren.get(position).getName();
            }
        });

        mTabLayout.setupWithViewPager(mViewPage);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPage.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
