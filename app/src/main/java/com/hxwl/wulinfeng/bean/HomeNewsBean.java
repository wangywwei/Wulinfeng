package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/5/31.
 * 首页 -- 最新
 */

public class HomeNewsBean {
    private String id;

    private String type;

    private String title;

    private String img;

    private String hid;

    private String is_tuijian;

    private String tuijian_start_time;

    private String tuijian_end_time;

    private String is_show;

    private String sort;

    private String time;

    private String label;

    private String zhuanti_id;

    private String zan_times;

    private String huifu_times;

    private String open_times;

    private String video_count;

    private String dingshi_send;

    private String type_;

    private Videos videos;

    private String contents;

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

    public String getIs_tuijian() {
        return is_tuijian;
    }

    public void setIs_tuijian(String is_tuijian) {
        this.is_tuijian = is_tuijian;
    }

    public String getTuijian_start_time() {
        return tuijian_start_time;
    }

    public void setTuijian_start_time(String tuijian_start_time) {
        this.tuijian_start_time = tuijian_start_time;
    }

    public String getTuijian_end_time() {
        return tuijian_end_time;
    }

    public void setTuijian_end_time(String tuijian_end_time) {
        this.tuijian_end_time = tuijian_end_time;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getZhuanti_id() {
        return zhuanti_id;
    }

    public void setZhuanti_id(String zhuanti_id) {
        this.zhuanti_id = zhuanti_id;
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

    public String getVideo_count() {
        return video_count;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public String getDingshi_send() {
        return dingshi_send;
    }

    public void setDingshi_send(String dingshi_send) {
        this.dingshi_send = dingshi_send;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "HomeNewsBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", hid='" + hid + '\'' +
                ", is_tuijian='" + is_tuijian + '\'' +
                ", tuijian_start_time='" + tuijian_start_time + '\'' +
                ", tuijian_end_time='" + tuijian_end_time + '\'' +
                ", is_show='" + is_show + '\'' +
                ", sort='" + sort + '\'' +
                ", time='" + time + '\'' +
                ", label='" + label + '\'' +
                ", zhuanti_id='" + zhuanti_id + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", open_times='" + open_times + '\'' +
                ", video_count='" + video_count + '\'' +
                ", dingshi_send='" + dingshi_send + '\'' +
                ", type_='" + type_ + '\'' +
                ", videos=" + videos +
                ", contents='" + contents + '\'' +
                '}';
    }
}
