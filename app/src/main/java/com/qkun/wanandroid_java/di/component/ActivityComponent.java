package com.qkun.wanandroid_java.di.component;

import android.app.Activity;
import android.content.Context;

import com.qkun.wanandroid_java.di.mudule.ActivityModule;
import com.qkun.wanandroid_java.di.scope.ContextLife;
import com.qkun.wanandroid_java.di.scope.PerActivity;
import com.qkun.wanandroid_java.ui.login.LoginActivity;

import dagger.Component;

/**
 * Created by QKun on 2018/10/31.
 * 通过 inject 方法 来将依赖提供方 注入到 依赖需求方实例中
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    //只要获取ActivityActivityComponent  就可以在下面的类中依赖成功
    void inject(LoginActivity activity);
}
