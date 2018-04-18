package com.hxwl.wulinfeng.bean;

import java.io.Serializable;

/**
 * Created by Allen on 2017/6/22.
 * 往期视频 -- 视频信息
 */
public class WQShiPinBean implements Serializable{
    private String url_type;
    private String saishi_id;
    private String title;
    private String saicheng_id;
    private String is_tiaozhuan;
    private String id;
    private String bofangliang;
    private String url;
    private String name;
    private String zhibo_time;
    private String img;
    private String shichang;
    private boolean select ;

    public boolean isSelect() {
        return select;
    }

    public String getUrl_type() {
        return url_type;
    }

    public void setUrl_type(String url_type) {
        this.url_type = url_type;
    }

    public String getSaishi_id() {
        return saishi_id;
    }

    public void setSaishi_id(String saishi_id) {
        this.saishi_id = saishi_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSaicheng_id() {
        return saicheng_id;
    }

    public void setSaicheng_id(String saicheng_id) {
        this.saicheng_id = saicheng_id;
    }

    public String getIs_tiaozhuan() {
        return is_tiaozhuan;
    }

    public void setIs_tiaozhuan(String is_tiaozhuan) {
        this.is_tiaozhuan = is_tiaozhuan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBofangliang() {
        return bofangliang;
    }

    public void setBofangliang(String bofangliang) {
        this.bofangliang = bofangliang;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhibo_time() {
        return zhibo_time;
    }

    public void setZhibo_time(String zhibo_time) {
        this.zhibo_time = zhibo_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShichang() {
        return shichang;
    }

    public void setShichang(String shichang) {
        this.shichang = shichang;
    }

    @Override
    public String toString() {
        return "WQShiPinBean{" +
                "url_type='" + url_type + '\'' +
                ", saishi_id='" + saishi_id + '\'' +
                ", title='" + title + '\'' +
                ", saicheng_id='" + saicheng_id + '\'' +
                ", is_tiaozhuan='" + is_tiaozhuan + '\'' +
                ", id='" + id + '\'' +
                ", bofangliang='" + bofangliang + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", zhibo_time='" + zhibo_time + '\'' +
                ", img='" + img + '\'' +
                ", shichang='" + shichang + '\'' +
                '}';
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
