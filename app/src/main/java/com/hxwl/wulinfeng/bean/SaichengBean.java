package com.hxwl.wulinfeng.bean;

import android.app.Service;

import java.io.Serializable;

/**
 * Created by Allen on 2017/6/7.
 */
public class SaichengBean implements Serializable{
    private String id;

    private String saishi_id;

    private String city;

    private String saicheng_id ;

    public String getSaicheng_id() {
        return saicheng_id;
    }

    public void setSaicheng_id(String saicheng_id) {
        this.saicheng_id = saicheng_id;
    }

    private String name;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String changguan_id;

    private String start_time;

    private String saicheng_img;

    private String title;

    private String state;

    private String yuyue_state ; //empty

    private String saishi_name;

    private String changguan_addr;

    private String video_type;
    private String tuiliu_url;

    private String yugao_url;

    private String type;

    private String yugao_url_type;


    private String end_time;


    private String yugao_url_is_tiaozhuan;

    private String shoupiao_url ;


    public String getShoupiao_url() {
        return shoupiao_url;
    }

    public void setShoupiao_url(String shoupiao_url) {
        this.shoupiao_url = shoupiao_url;
    }

    private String is_tiaozhuan;

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
        return "SaichengBean{" +
                "id='" + id + '\'' +
                ", saishi_id='" + saishi_id + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", changguan_id='" + changguan_id + '\'' +
                ", start_time='" + start_time + '\'' +
                ", saicheng_img='" + saicheng_img + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", yuyue_state='" + yuyue_state + '\'' +
                ", saishi_name='" + saishi_name + '\'' +
                ", changguan_addr='" + changguan_addr + '\'' +
                ", video_type='" + video_type + '\'' +
                '}';
    }

    public String getYuyue_state() {
        return yuyue_state;
    }

    public void setYuyue_state(String yuyue_state) {
        this.yuyue_state = yuyue_state;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChangguan_id() {
        return changguan_id;
    }

    public void setChangguan_id(String changguan_id) {
        this.changguan_id = changguan_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSaicheng_img() {
        return saicheng_img;
    }

    public void setSaicheng_img(String saicheng_img) {
        this.saicheng_img = saicheng_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSaishi_name() {
        return saishi_name;
    }

    public void setSaishi_name(String saishi_name) {
        this.saishi_name = saishi_name;
    }

    public String getChangguan_addr() {
        return changguan_addr;
    }

    public void setChangguan_addr(String changguan_addr) {
        this.changguan_addr = changguan_addr;
    }

}
