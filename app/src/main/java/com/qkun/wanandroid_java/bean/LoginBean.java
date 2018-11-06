package com.qkun.wanandroid_java.bean;

import java.util.List;

/**
 * Created by QKun on 2018/11/6.
 */
public class LoginBean {

    /**
     * data : {"chapterTops":[],"collectIds":[2332,2250,2682,1774,2703,1271,2868,2871,2870,2927,2979,1755,1369,3146,3142,3226,3221,3241,3283,3288,3189,3342,3417,3446,3458,3459,3460,3447,7431,7433],"email":"","icon":"","id":520,"password":"*******","token":"","type":0,"username":"藕塘里的咸鱼"}
     * errorCode : 0
     * errorMsg :
     */


    /**
     * chapterTops : []
     * collectIds : [2332,2250,2682,1774,2703,1271,2868,2871,2870,2927,2979,1755,1369,3146,3142,3226,3221,3241,3283,3288,3189,3342,3417,3446,3458,3459,3460,3447,7431,7433]
     * email :
     * icon :
     * id : 520
     * password : *******
     * token :
     * type : 0
     * username : 藕塘里的咸鱼
     */

    private String email;
    private String icon;
    private int id;
    private String password;
    private String token;
    private int type;
    private String username;
    private List<?> chapterTops;
    private List<Integer> collectIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
