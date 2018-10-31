package com.qkun.wanandroid_java.di.component;

import android.content.Context;

import com.qkun.wanandroid_java.di.mudule.ApplicationModule;
import com.qkun.wanandroid_java.di.scope.ContextLife;
import com.qkun.wanandroid_java.di.scope.PerApp;

import dagger.Component;

/**
 * Created by QKun on 2018/10/31.
 *单例是在同一个Component实例提供依赖的前提下才有效的,
 *   ApplicationComponent:作用 给其他Component提供依赖
 *        ApplicationModule:提供Application对象
 *
 *    奇怪？ 怎么没有inject方法？？
 *   解释：我们用inject方法依赖需求方实例送到Component中，从而帮助依赖需求方实现依赖
 *    但我们这个ApplicationComponent 是给其他Component提供依赖的，所以我们就可以不用
 *    inject方法，但是多了一个getApplication方法 返回一个context对象
 *    这个方法用什么作用了？
 *    它的作用就告诉依赖ApplicationComponent的Component,ApplicationModule能为你们提供Context对象
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getApplication();
}
