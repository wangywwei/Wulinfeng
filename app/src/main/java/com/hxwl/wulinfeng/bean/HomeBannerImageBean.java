package com.hxwl.wulinfeng.bean;

import java.io.Serializable;

/**
 * Created by Allen on 2017/5/31.
 * 首页轮播图bean
 */

public class HomeBannerImageBean implements Serializable {
    private String id;

    private String img;

    private String title;
    private String label;//标签

    private String label2;//标签

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String android_url;

    private String tongyong_url ;

    public String getTongyong_url() {
        return tongyong_url;
    }

    public void setTongyong_url(String tongyong_url) {
        this.tongyong_url = tongyong_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    @Override
    public String toString() {
        return "HomeBannerImageBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", android_url='" + android_url + '\'' +
                '}';
    }
}
