package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/7.
 */
public class SaiChengBannereBean {
    private String id;

    private String img;

    private String title;

    private String android_url;

    private String ios_url;

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

    public String getIos_url() {
        return ios_url;
    }

    public void setIos_url(String ios_url) {
        this.ios_url = ios_url;
    }

    @Override
    public String toString() {
        return "SaiChengBannereBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", android_url='" + android_url + '\'' +
                ", ios_url='" + ios_url + '\'' +
                '}';
    }
}
