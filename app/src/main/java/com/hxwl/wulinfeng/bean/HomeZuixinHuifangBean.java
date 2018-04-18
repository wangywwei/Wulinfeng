package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/5/31.
 * 首页最新回放bean
 */

public class HomeZuixinHuifangBean {
    private String id;

    private String saishi_id;

    private String saicheng_id;

    private String name;

    private String title;

    private String img;

    private String shichang;

    private String url;

    private String url_type;

    private String bofangliang;

    private String is_tiaozhuan;

    private String zhibo_time;

    private String saishi_name;

    private boolean isSelect ;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSaishi_name() {
        return saishi_name;
    }

    public void setSaishi_name(String saishi_name) {
        this.saishi_name = saishi_name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getShichang() {
        return shichang;
    }

    public void setShichang(String shichang) {
        this.shichang = shichang;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_type() {
        return url_type;
    }

    public void setUrl_type(String url_type) {
        this.url_type = url_type;
    }

    public String getBofangliang() {
        return bofangliang;
    }

    public void setBofangliang(String bofangliang) {
        this.bofangliang = bofangliang;
    }

    public String getIs_tiaozhuan() {
        return is_tiaozhuan;
    }

    public void setIs_tiaozhuan(String is_tiaozhuan) {
        this.is_tiaozhuan = is_tiaozhuan;
    }

    public String getZhibo_time() {
        return zhibo_time;
    }

    public void setZhibo_time(String zhibo_time) {
        this.zhibo_time = zhibo_time;
    }

    @Override
    public String toString() {
        return "HomeZuixinHuifangBean{" +
                "id='" + id + '\'' +
                ", saishi_id='" + saishi_id + '\'' +
                ", saicheng_id='" + saicheng_id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", shichang='" + shichang + '\'' +
                ", url='" + url + '\'' +
                ", url_type='" + url_type + '\'' +
                ", bofangliang='" + bofangliang + '\'' +
                ", is_tiaozhuan='" + is_tiaozhuan + '\'' +
                ", zhibo_time='" + zhibo_time + '\'' +
                ", saishi_name='" + saishi_name + '\'' +
                '}';
    }

}
