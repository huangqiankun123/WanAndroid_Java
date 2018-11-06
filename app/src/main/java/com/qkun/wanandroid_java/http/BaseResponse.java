package com.qkun.wanandroid_java.http;

/**
 * Created by QKun on 2018/3/22.
 * 网络请求结果 基类
 */

public class BaseResponse<T> {
    private int errorCode;
    private String errorMsg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断请求是否成功 大于0 则为成功
     *
     * @return
     */
    public boolean isSuccess() {
        return errorCode == 0;
    }
}
