package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/5.
 * 混合列表里边news数据bean
 */
public class AllHomeHunHeBean {
    private String id;

    private String type;

    private String title;

    private String img;
    private String Type_name;

    private boolean isSelect ;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getType_name() {
        return Type_name;
    }

    public void setType_name(String type_name) {
        Type_name = type_name;
    }

    private List<TujiImgs> imgs;

    public List<TujiImgs> getImgs() {
        return imgs;
    }

    public void setImgs(List<TujiImgs> imgs) {
        this.imgs = imgs;
    }

    private List<String> img_contents;

    public List<String> getImg_contents() {
        return img_contents;
    }

    public void setImg_contents(List<String> img_contents) {
        this.img_contents = img_contents;
    }

    public String getFengmiantu() {
        return fengmiantu;
    }


    public void setFengmiantu(String fengmiantu) {
        this.fengmiantu = fengmiantu;
    }


    private String fengmiantu;

    private String hid;

    private String time;

    private String label;

    private String zan_times;

    private String huifu_times;

    private String open_times;

    private String type_;

    private Videos videos;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getType_() {
        return type_;
    }

    public void setType_(String type_) {
        this.type_ = type_;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "AllHomeHunHeBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", hid='" + hid + '\'' +
                ", time='" + time + '\'' +
                ", label='" + label + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", type_='" + type_ + '\'' +
                ", videos=" + videos +
                '}';
    }
}
