package com.qkun.wanandroid_java.di.component;

import android.app.Activity;
import android.content.Context;

import com.qkun.wanandroid_java.di.mudule.FragmentModule;
import com.qkun.wanandroid_java.di.scope.ContextLife;
import com.qkun.wanandroid_java.di.scope.PerFragment;
import com.qkun.wanandroid_java.ui.home.HomeFragment;
import com.qkun.wanandroid_java.ui.knowledge.KnowledgeFragment;
import com.qkun.wanandroid_java.ui.knowledge.knowledgelist.KnowledgeListFragment;

import dagger.Component;

/**
 * Created by QKun on 2018/10/31.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    //进行 inject 注入
    void inject(HomeFragment fragment);

    void inject(KnowledgeFragment fragment);

    void inject(KnowledgeListFragment fragment);
}
