package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/14.
 * 我--系统消息bean
 */
public class SystemMessageBean {
    private String id;

    private String title;

    private String contents;

    private String img;

    private String time;

    private String ios_tiaozhuan_url;

    private String android_tiaozhuan_url;

    private String tongyong_url;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIos_tiaozhuan_url() {
        return ios_tiaozhuan_url;
    }

    public void setIos_tiaozhuan_url(String ios_tiaozhuan_url) {
        this.ios_tiaozhuan_url = ios_tiaozhuan_url;
    }

    public String getAndroid_tiaozhuan_url() {
        return android_tiaozhuan_url;
    }

    public void setAndroid_tiaozhuan_url(String android_tiaozhuan_url) {
        this.android_tiaozhuan_url = android_tiaozhuan_url;
    }

    @Override
    public String toString() {
        return "SystemMessageBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", img='" + img + '\'' +
                ", time='" + time + '\'' +
                ", ios_tiaozhuan_url='" + ios_tiaozhuan_url + '\'' +
                ", android_tiaozhuan_url='" + android_tiaozhuan_url + '\'' +
                '}';
    }
}
