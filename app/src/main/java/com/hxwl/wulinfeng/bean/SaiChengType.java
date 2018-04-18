package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/7.
 * 赛程列表上方Tab 分类
 */
public class SaiChengType {
    private String id;

    private String name;

    private String icon;

    private String fang_icon;

    private String jianjie;

    private String lastVideoTime;

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
}
