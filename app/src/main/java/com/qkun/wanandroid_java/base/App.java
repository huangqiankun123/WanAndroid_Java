package com.qkun.wanandroid_java.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.qkun.wanandroid_java.di.component.ApplicationComponent;
import com.qkun.wanandroid_java.di.component.DaggerApplicationComponent;
import com.qkun.wanandroid_java.di.mudule.ApplicationModule;

/**
 * Created by QKun on 2018/10/31.
 */
public class App extends Application {
    private static App mInstance;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initApplicationComponent();
        Utils.init(this);
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
     * @return
     */
    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }
}
