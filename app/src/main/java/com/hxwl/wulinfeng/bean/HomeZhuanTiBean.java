package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/5/31.
 * 首页专题bean 统一
 */

public class HomeZhuanTiBean {
    private String id;

    private String title;

    private String img;

    private List<AllZhuanTiBean> news;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<AllZhuanTiBean> getNews() {
        return news;
    }

    public void setNews(List<AllZhuanTiBean> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "HomeZhuanTiBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", news=" + news +
                '}';
    }
}
