package com.qkun.wanandroid_java.bean;

import java.io.Serializable;

/**
 * Created by QKun on 2018/11/8.
 */
public class CollectStatus implements Serializable {


    /**
     * errorCode : -1001
     * errorMsg : 请先登录！
     */

    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
