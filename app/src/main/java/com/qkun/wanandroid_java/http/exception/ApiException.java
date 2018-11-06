package com.qkun.wanandroid_java.http.exception;

/**
 * Created by QKun on 2018/11/6.
 */
public class ApiException extends RuntimeException {
    private int code;
    private String msg;

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
