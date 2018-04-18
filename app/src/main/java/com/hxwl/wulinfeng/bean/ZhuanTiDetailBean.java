package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/28.
 * 专题详情
 */
public class ZhuanTiDetailBean {
    private String id;
    private String type;
    private String title;
    private String open_times;
    private String fengmiantu;
    private String time;
    private String type_;
    private String type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpen_times() {
        return open_times;
    }

    public void setOpen_times(String open_times) {
        this.open_times = open_times;
    }

    public String getFengmiantu() {
        return fengmiantu;
    }

    public void setFengmiantu(String fengmiantu) {
        this.fengmiantu = fengmiantu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType_() {
        return type_;
    }

    public void setType_(String type_) {
        this.type_ = type_;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public String toString() {
        return "ZhuanTiDetailBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", open_times='" + open_times + '\'' +
                ", fengmiantu='" + fengmiantu + '\'' +
                ", time='" + time + '\'' +
                ", type_='" + type_ + '\'' +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
