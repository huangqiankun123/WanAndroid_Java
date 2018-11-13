package com.qkun.wanandroid_java.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by QKun on 2018/11/13.
 */
@Entity
public class HistoryModel {
    @Id(autoincrement = true)
    private long id;

    private String name;

    private Date date;

    @Generated(hash = 172238747)
    public HistoryModel(long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Generated(hash = 1184686390)
    public HistoryModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
