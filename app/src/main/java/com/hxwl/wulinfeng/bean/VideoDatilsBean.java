package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/29.
 */
public class VideoDatilsBean {
    private String id;

    private String type;

    private String title;

    private String img;

    private String hid;

    private String time;

    private String zan_times;

    private String huifu_times;

    private String open_times;

    private Videos videos;

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

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public Huser getHuser() {
        return huser;
    }

    public void setHuser(Huser huser) {
        this.huser = huser;
    }

    @Override
    public String toString() {
        return "VideoDatilsBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", hid='" + hid + '\'' +
                ", time='" + time + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", videos=" + videos +
                ", huser=" + huser +
                '}';
    }
}
