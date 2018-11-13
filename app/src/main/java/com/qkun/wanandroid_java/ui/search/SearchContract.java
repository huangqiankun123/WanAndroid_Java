package com.qkun.wanandroid_java.ui.search;

import com.qkun.wanandroid_java.base.BaseContract;
import com.qkun.wanandroid_java.db.HistoryModel;

import java.util.List;

/**
 * Created by QKun on 2018/11/12.
 */
public interface SearchContract {

    interface View extends BaseContract.BaseIView {
        void getHotkey(List<String> strings, List<HistoryModel> modelList);

        void addHistorySuccess(HistoryModel historyModel);

    }

    interface Presenter extends BaseContract.BaseIPresent<SearchContract.View> {
        void loadHotkey();

        void addHistory(String name);
        //删除一个
        void deleteData(int position);
        //删除全部
        void deleteDataAll();

    }
}
