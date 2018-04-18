package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 * 俱乐部详情
 */
public class ClubDetailsBean {
    private String id;

    private String name;

    private String icon;

    private String jianjie;

    private String mingxingxueyuan;

    private String addr;

    private Zhanji zhanji;

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

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public String getMingxingxueyuan() {
        return mingxingxueyuan;
    }

    public void setMingxingxueyuan(String mingxingxueyuan) {
        this.mingxingxueyuan = mingxingxueyuan;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Zhanji getZhanji() {
        return zhanji;
    }

    @Override
    public String toString() {
        return "ClubListBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", jianjie='" + jianjie + '\'' +
                ", mingxingxueyuan='" + mingxingxueyuan + '\'' +
                ", addr='" + addr + '\'' +
                ", zhanji=" + zhanji +
                '}';
    }

    public void setZhanji(Zhanji zhanji) {
        this.zhanji = zhanji;


    }
}
