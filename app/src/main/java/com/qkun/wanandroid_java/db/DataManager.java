package com.qkun.wanandroid_java.db;

import com.qkun.wanandroid_java.base.App;
import com.qkun.wanandroid_java.dao.HistoryModelDao;

import java.util.List;

/**
 * Created by QKun on 2018/11/13.
 */
public class DataManager {
    /**
     * 增
     *
     * @param model 对象
     */
    public static void insertData(HistoryModel model) {
        //insertOrReplace()**数据存在则替换，数据不存在则插入
        App.getDaoSession().getHistoryModelDao().insertOrReplace(model);
    }

    /**
     * 删
     *
     * @param id 主键
     */
    public static void deleteData(long id) {
        App.getDaoSession().getHistoryModelDao().deleteByKey(id);
    }

    /**
     * 全部删除
     */
    public static void deleteDataAll() {
        App.getDaoSession().getHistoryModelDao().deleteAll();
    }

    /**
     * 查询全部
     *
     * @return
     */
    public static List<HistoryModel> queryAll() {
        return App.getDaoSession().getHistoryModelDao().queryBuilder().orderDesc(HistoryModelDao.Properties.Id).list();
    }

    /**
     * 根据名字来查询 数据库中是否有值
     * @param name
     * @return
     */
    public static HistoryModel queryByName(String name) {
        HistoryModel historyModel = App.getDaoSession().getHistoryModelDao().queryBuilder().where(HistoryModelDao.Properties.Name.eq(name)).unique();
        return historyModel;
    }

}
