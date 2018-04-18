package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/2.
 * 首页 -- 图集 listview bean
 */
public class TuJiBean {
    private String id;

    private String type;

    private String title;

    private String hid;

    private String label;

    private String huifu_times;

    private String open_times;

    private String zan_times;

    private String time;

    private List<TujiImgs> imgs ;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getZan_times() {
        return zan_times;
    }

    public void setZan_times(String zan_times) {
        this.zan_times = zan_times;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<TujiImgs> getImgs() {
        return imgs;
    }

    public void setImgs(List<TujiImgs> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "TuJiBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", hid='" + hid + '\'' +
                ", label='" + label + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", time='" + time + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
