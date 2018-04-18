package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/14.
 * 发现上方的布局点击之后的页面接口
 */
public class BiSaiShiPinBean {
    private String id;

    private String name;

    private String icon;

    private String fang_icon;

    private String jianjie;

    private String lastVideoTime;

    private String lastVideoTime_format;

    private String lastVideoId ;

    public String getLastVideoId() {
        return lastVideoId;
    }

    public void setLastVideoId(String lastVideoId) {
        this.lastVideoId = lastVideoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFang_icon() {
        return fang_icon;
    }

    public void setFang_icon(String fang_icon) {
        this.fang_icon = fang_icon;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public String getLastVideoTime() {
        return lastVideoTime;
    }

    public void setLastVideoTime(String lastVideoTime) {
        this.lastVideoTime = lastVideoTime;
    }

    public String getLastVideoTime_format() {
        return lastVideoTime_format;
    }

    public void setLastVideoTime_format(String lastVideoTime_format) {
        this.lastVideoTime_format = lastVideoTime_format;
    }

    @Override
    public String toString() {
        return "BiSaiShiPinBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", fang_icon='" + fang_icon + '\'' +
                ", jianjie='" + jianjie + '\'' +
                ", lastVideoTime='" + lastVideoTime + '\'' +
                ", lastVideoTime_format='" + lastVideoTime_format + '\'' +
                '}';
    }
}
