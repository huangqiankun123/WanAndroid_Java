package com.qkun.wanandroid_java.di.mudule;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.qkun.wanandroid_java.di.scope.ContextLife;
import com.qkun.wanandroid_java.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by QKun on 2018/10/31.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
