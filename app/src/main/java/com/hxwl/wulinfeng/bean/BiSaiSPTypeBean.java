package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/7/3.
 */
public class BiSaiSPTypeBean {
    private String id;

    private String type;

    private String title;

    private String img;

    private String open_times;

    private String time;

    private Videos videos;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOpen_times() {
        return open_times;
    }

    public void setOpen_times(String open_times) {
        this.open_times = open_times;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public String toString() {
        return "BiSaiSPTypeBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", open_times='" + open_times + '\'' +
                ", time='" + time + '\'' +
                ", videos=" + videos +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
