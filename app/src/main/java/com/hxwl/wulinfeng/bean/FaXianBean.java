package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/14.
 * 发现活动bean
 */
public class FaXianBean {
    private String id;

    private String icon;

    private String name;

    private String android_url;

    private String ios_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "FaXianBean{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", android_url='" + android_url + '\'' +
                ", ios_url='" + ios_url + '\'' +
                '}';
    }
}
