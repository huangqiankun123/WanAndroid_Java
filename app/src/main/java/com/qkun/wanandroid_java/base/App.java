package com.qkun.wanandroid_java.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.Utils;
import com.qkun.wanandroid_java.R;
import com.qkun.wanandroid_java.constant.Constant;
import com.qkun.wanandroid_java.dao.DaoMaster;
import com.qkun.wanandroid_java.dao.DaoSession;
import com.qkun.wanandroid_java.di.component.ApplicationComponent;
import com.qkun.wanandroid_java.di.component.DaggerApplicationComponent;
import com.qkun.wanandroid_java.di.mudule.ApplicationModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.greendao.AbstractDaoMaster;

/**
 * Created by QKun on 2018/10/31.
 */
public class App extends Application {
    private static App mInstance;
    private ApplicationComponent mApplicationComponent;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initApplicationComponent();
        Utils.init(this);
        setNightMode();
        initDatabase();
    }

    private void initDatabase() {
        //创建数据库history.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "wanandroid.db", null);
        //获取可写数据库
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        //获取dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }


    /**
     * 初始化夜间模式
     */
    private void setNightMode() {
        boolean nightMode = SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.NIGHT_KEY);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * BaseActivity和BaseFragment中能获取到同一个ApplicationComponent实例
     * 在此提供方法给BaseActivity和BaseFragment 并保证获取ApplicationComponent 是唯一的
     *
     * @return
     */
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
