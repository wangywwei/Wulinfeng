package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/26.
 * 图集详情界面上方bean
 */
public class TuJiDatilsBean {
    private String id;

    private String type;

    private String title;

    private String hid;

    private String time;

    private String zan_times;

    private String huifu_times;

    private String open_times;

    private String img_count;

    private List<String> imgs ;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getOpen_times() {
        return open_times;
    }

    public void setOpen_times(String open_times) {
        this.open_times = open_times;
    }

    public String getImg_count() {
        return img_count;
    }

    public void setImg_count(String img_count) {
        this.img_count = img_count;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public Huser getHuser() {
        return huser;
    }

    public void setHuser(Huser huser) {
        this.huser = huser;
    }

    @Override
    public String toString() {
        return "TuJiDatilsBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", hid='" + hid + '\'' +
                ", time='" + time + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", img_count='" + img_count + '\'' +
                ", imgs=" + imgs +
                ", huser=" + huser +
                '}';
    }
}
