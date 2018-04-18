package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/1.
 * 资讯listview的bean类
 */
public class ZiXunBean {
    private String id;

    private String type;

    private String title;

    private String contents;

    private String hid;

    private String label;

    private String huifu_times;

    private String open_times;

    private String zan_times;

    private String fengmiantu;

    private String time;

    private String title_contents;

    private List<String> img_contents ;

    public String getHuifu_times() {
        return huifu_times;
    }

    public void setHuifu_times(String huifu_times) {
        this.huifu_times = huifu_times;
    }

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getTitle_contents() {
        return title_contents;
    }

    public void setTitle_contents(String title_contents) {
        this.title_contents = title_contents;
    }

    public List<String> getImg_contents() {
        return img_contents;
    }

    public void setImg_contents(List<String> img_contents) {
        this.img_contents = img_contents;
    }

    @Override
    public String toString() {
        return "ZiXunBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", hid='" + hid + '\'' +
                ", label='" + label + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", fengmiantu='" + fengmiantu + '\'' +
                ", time='" + time + '\'' +
                ", title_contents='" + title_contents + '\'' +
                ", img_contents=" + img_contents +
                '}';
    }
}
