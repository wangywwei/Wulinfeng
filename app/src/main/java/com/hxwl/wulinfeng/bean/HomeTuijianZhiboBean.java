package com.hxwl.wulinfeng.bean;

import java.io.Serializable;

/**
 * Created by Allen on 2017/5/31.
 * 首页推荐直播bean
 */

public class HomeTuijianZhiboBean implements Serializable {
    private String id;

    private String saishi_id;

    private String saicheng_id;

    private String title;

    private String image;

    private String url;

    private String tuiliu_url;

    private String yugao_url;

    private String type;

    private String yugao_url_type;

    private String start_time;

    private String end_time;

    private String state;

    private String yugao_url_is_tiaozhuan;

    private String is_tiaozhuan;

    private String video_type;
    private String changguan_addr;

    private String yuyue_state;

    public void setYuyue_state(String yuyue_state) {
        this.yuyue_state = yuyue_state;
    }

    public String getChangguan_addr() {
        return changguan_addr;
    }

    public void setChangguan_addr(String changguan_addr) {
        this.changguan_addr = changguan_addr;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaishi_id() {
        return saishi_id;
    }

    public void setSaishi_id(String saishi_id) {
        this.saishi_id = saishi_id;
    }

    public String getSaicheng_id() {
        return saicheng_id;
    }

    public void setSaicheng_id(String saicheng_id) {
        this.saicheng_id = saicheng_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTuiliu_url() {
        return tuiliu_url;
    }

    public void setTuiliu_url(String tuiliu_url) {
        this.tuiliu_url = tuiliu_url;
    }

    public String getYugao_url() {
        return yugao_url;
    }

    public void setYugao_url(String yugao_url) {
        this.yugao_url = yugao_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYugao_url_type() {
        return yugao_url_type;
    }

    public void setYugao_url_type(String yugao_url_type) {
        this.yugao_url_type = yugao_url_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getYugao_url_is_tiaozhuan() {
        return yugao_url_is_tiaozhuan;
    }

    public void setYugao_url_is_tiaozhuan(String yugao_url_is_tiaozhuan) {
        this.yugao_url_is_tiaozhuan = yugao_url_is_tiaozhuan;
    }

    public String getIs_tiaozhuan() {
        return is_tiaozhuan;
    }

    public void setIs_tiaozhuan(String is_tiaozhuan) {
        this.is_tiaozhuan = is_tiaozhuan;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    @Override
    public String toString() {
        return "HomeTuijianZhiboBean{" +
                "id='" + id + '\'' +
                ", saishi_id='" + saishi_id + '\'' +
                ", saicheng_id='" + saicheng_id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", tuiliu_url='" + tuiliu_url + '\'' +
                ", yugao_url='" + yugao_url + '\'' +
                ", type='" + type + '\'' +
                ", yugao_url_type='" + yugao_url_type + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", state='" + state + '\'' +
                ", yugao_url_is_tiaozhuan='" + yugao_url_is_tiaozhuan + '\'' +
                ", is_tiaozhuan='" + is_tiaozhuan + '\'' +
                ", video_type='" + video_type + '\'' +
                '}';
    }

    public String getYuyue_state() {
        return yuyue_state;
    }
}
