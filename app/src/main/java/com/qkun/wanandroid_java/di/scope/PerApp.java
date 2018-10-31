package com.qkun.wanandroid_java.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by QKun on 2018/3/12.
 * Application 单例 其实可以不要的
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
