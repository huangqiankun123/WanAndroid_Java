package com.qkun.wanandroid_java.ui.search;

import com.blankj.utilcode.util.LogUtils;
import com.qkun.wanandroid_java.base.BasePresenter;
import com.qkun.wanandroid_java.bean.HotKeyBean;
import com.qkun.wanandroid_java.db.DataManager;
import com.qkun.wanandroid_java.db.HistoryModel;
import com.qkun.wanandroid_java.http.ApiService;
import com.qkun.wanandroid_java.http.BaseObserver;
import com.qkun.wanandroid_java.http.RetrofitManager;
import com.qkun.wanandroid_java.http.RxSchedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by QKun on 2018/11/12.
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    @Inject
    public SearchPresenter() {
    }

    @Override
    public void loadHotkey() {

        RetrofitManager.createApi(ApiService.class)
                .getHotkey()
                .compose(RxSchedulers.<List<HotKeyBean>>applySchedulers())
                .compose(mView.<List<HotKeyBean>>bindToLife())
                .subscribe(new BaseObserver<List<HotKeyBean>>() {
                    @Override
                    public void _onNext(List<HotKeyBean> beans) {
                        List<String> strings = new ArrayList<>();
                        if (!beans.isEmpty() && beans.size() != 0) {
                            for (HotKeyBean bean : beans) {
                                strings.add(bean.getName());
                            }
                        }
                        List<HistoryModel> historyModels = DataManager.queryAll();
                        mView.getHotkey(strings, historyModels);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showFailed(msg);
                    }
                });
    }

    @Override
    public void addHistory(String name) {
        HistoryModel historyModel = DataManager.queryByName(name);
        if (historyModel != null) {
            mView.addHistorySuccess(historyModel);
        } else {
            HistoryModel model = new HistoryModel();
            model.setId(System.currentTimeMillis());
            model.setName(name);
            model.setDate(new Date());
            DataManager.insertData(model);
            mView.addHistorySuccess(model);
        }

    }

    @Override
    public void deleteData(int position) {
        List<HistoryModel> historyModels = DataManager.queryAll();
        HistoryModel model = historyModels.get(position);
        DataManager.deleteData(model.getId());
    }

    @Override
    public void deleteDataAll() {
        List<HistoryModel> historyModels = DataManager.queryAll();
        if (!historyModels.isEmpty() && historyModels.size() != 0) {
            DataManager.deleteDataAll();
        }

    }


}
