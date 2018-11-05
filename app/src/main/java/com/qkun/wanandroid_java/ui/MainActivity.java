package com.qkun.wanandroid_java.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.base.BaseActivity;
import com.qkun.wanandroid_java.base.BaseFragment;
import com.qkun.wanandroid_java.ui.home.HomeFragment;
import com.qkun.wanandroid_java.ui.knowledge.KnowledgeFragment;
import com.qkun.wanandroid_java.ui.navigation.NavigationFragment;
import com.qkun.wanandroid_java.ui.project.ProjectFragment;
import com.qkun.wanandroid_java.ui.search.SearchActivity;
import com.qkun.wanandroid_java.ui.wechat.WeChatFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.bottom_navigation)
    BottomNavigationBar mBottomNavigation;
    @BindView(R.id.nav_view)
    NavigationView mNavView;

    List<BaseFragment> mFragments = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.app_name));

        initFragment();
        setDefaultFragment();
        initBottomNavigation();

        initDrawerLayout();
        initNavigation();
    }


    private void initFragment() {
        mFragments.add(HomeFragment.newInstance(getString(R.string.home)));
        mFragments.add(KnowledgeFragment.newInstance(getString(R.string.knowledge_system)));
        mFragments.add(WeChatFragment.newInstance(getString(R.string.wechat)));
        mFragments.add(NavigationFragment.newInstance(getString(R.string.navigation)));
        mFragments.add(ProjectFragment.newInstance(getString(R.string.project)));
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, mFragments.get(0));
        ft.commit();
    }

    private void initBottomNavigation() {
        mBottomNavigation.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigation.setActiveColor(R.color.colorAccent)
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, getString(R.string.home)))
                .addItem(new BottomNavigationItem(R.drawable.ic_apps_black_24dp, getString(R.string.knowledge_system)))
                .addItem(new BottomNavigationItem(R.drawable.ic_wechat_black_24dp, getString(R.string.wechat)))
                .addItem(new BottomNavigationItem(R.drawable.ic_navigation_black_24dp, getString(R.string.navigation)))
                .addItem(new BottomNavigationItem(R.drawable.ic_project_black_24dp, getString(R.string.project)))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (mFragments != null) {
                    if (position <= mFragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment currentFragment = fm.findFragmentById(R.id.container);
                        BaseFragment nextFragment = mFragments.get(position);
                        if (nextFragment.isAdded()) {
                            ft.hide(currentFragment).show(nextFragment);
                        } else {
                            ft.hide(currentFragment).add(R.id.container, nextFragment);
                            if (nextFragment.isHidden()) {
                                ft.show(nextFragment);
                            }
                        }
                        ft.commitAllowingStateLoss();

                        switch (position) {
                            case 0:
                                mToolbar.setTitle(getString(R.string.home));
                                break;
                            case 1:
                                mToolbar.setTitle(getString(R.string.knowledge_system));
                                break;
                            case 2:
                                mToolbar.setTitle(getString(R.string.wechat));
                                break;
                            case 3:
                                mToolbar.setTitle(getString(R.string.navigation));
                                break;
                            case 4:
                                mToolbar.setTitle(getString(R.string.project));
                                break;
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {
                if (mFragments != null) {
                    if (position < mFragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment nextFragment = mFragments.get(position);
                        ft.hide(nextFragment);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigation() {
        TextView nav_username = mNavView.getHeaderView(0).findViewById(R.id.tv_username);
        //设置用户名字

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_collect:
                        Toast.makeText(MainActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about_us:
                        Toast.makeText(MainActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_night_mode:
                        Toast.makeText(MainActivity.this, "夜间模式", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_todo:
                        Toast.makeText(MainActivity.this, "todo", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}