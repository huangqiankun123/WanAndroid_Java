package com.qkun.wanandroid_java.ui.search;

import com.qkun.wanandroid_java.base.BaseContract;

import java.util.List;

/**
 * Created by QKun on 2018/11/12.
 */
public interface SearchContract {

    interface View extends BaseContract.BaseIView {
       void getHotkey(List<String> strings);
    }

    interface Presenter extends BaseContract.BaseIPresent<SearchContract.View> {
        void loadHotkey();
    }
}
