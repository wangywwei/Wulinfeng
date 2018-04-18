package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/30.
 */
public class DuizhenListBean {
    private String id;

    private String saishi_id;

    private String city;

    private String name;

    private String start_time;

    private String saicheng_img;

    private String title;

    private String state;

    private List<Duizhen> duizhen ;

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

    public List<Duizhen> getDuizhen() {
        return duizhen;
    }

    public void setDuizhen(List<Duizhen> duizhen) {
        this.duizhen = duizhen;
    }
}
