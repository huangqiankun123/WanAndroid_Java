package com.qkun.wanandroid_java.di.mudule;

import android.app.Activity;
import android.content.Context;

import com.qkun.wanandroid_java.di.scope.ContextLife;
import com.qkun.wanandroid_java.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by QKun on 2018/10/31.
 * 类似工厂 制造对象
 * 在该类上加上@Module
 * 标识生产对象的方法加上@provide
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return mActivity;
    }
}
