package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 */
public class ZiXunDatilsBean {
    private String id;

    private String type;

    private String fengmiantu;

    private String title;

    private String contents;

    private String hid;

    private String time;

    private String open_times;

    private String zan_times;

    private String huifu_times;

    private Huser huser;

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

    public String getFengmiantu() {
        return fengmiantu;
    }

    public void setFengmiantu(String fengmiantu) {
        this.fengmiantu = fengmiantu;
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

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpen_times() {
        return open_times;
    }

    public void setOpen_times(String open_times) {
        this.open_times = open_times;
    }

    public String getZan_times() {
        return zan_times;
    }

    public void setZan_times(String zan_times) {
        this.zan_times = zan_times;
    }

    public String getHuifu_times() {
        return huifu_times;
    }

    public void setHuifu_times(String huifu_times) {
        this.huifu_times = huifu_times;
    }

    public Huser getHuser() {
        return huser;
    }

    public void setHuser(Huser huser) {
        this.huser = huser;
    }

    @Override
    public String toString() {
        return "ZiXunDatilsBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", fengmiantu='" + fengmiantu + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", hid='" + hid + '\'' +
                ", time='" + time + '\'' +
                ", open_times='" + open_times + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", huser=" + huser +
                '}';
    }
}
