package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/2.
 * 首页 -- 视频listview bean
 */
public class ShiPinBean {
    private String id;

    private String type;

    private String title;

    private String img;

    private String hid;

    private String label;

    private String huifu_times;

    private String open_times;

    private String zan_times;

    private String time;

    private Videos videos;
    private String type_name;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "ShiPinBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", hid='" + hid + '\'' +
                ", label='" + label + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", time='" + time + '\'' +
                ", videos=" + videos +
                '}';
    }
}
