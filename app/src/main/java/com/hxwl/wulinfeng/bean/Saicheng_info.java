package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/14.
 */
public class Saicheng_info {
    private String saishi_id;

    private String changguan_id;

    private String start_time;

    private String saicheng_img;

    private String title;

    private String state;

    private String saishi_name;

    private String changguan_addr;

    public String getSaishi_id() {
        return saishi_id;
    }

    public void setSaishi_id(String saishi_id) {
        this.saishi_id = saishi_id;
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

    @Override
    public String toString() {
        return "Saicheng_info{" +
                "saishi_id='" + saishi_id + '\'' +
                ", changguan_id='" + changguan_id + '\'' +
                ", start_time='" + start_time + '\'' +
                ", saicheng_img='" + saicheng_img + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", saishi_name='" + saishi_name + '\'' +
                ", changguan_addr='" + changguan_addr + '\'' +
                '}';
    }
}
