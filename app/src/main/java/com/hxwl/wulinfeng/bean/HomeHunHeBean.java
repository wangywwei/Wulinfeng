package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/5.
 * 混合布局listbean
 */
public class HomeHunHeBean {
    private String lastNewsId;

    private List<AllHomeHunHeBean> news ;

    public String getLastNewsId() {
        return lastNewsId;
    }

    public void setLastNewsId(String lastNewsId) {
        this.lastNewsId = lastNewsId;
    }

    public List<AllHomeHunHeBean> getNews() {
        return news;
    }

    public void setNews(List<AllHomeHunHeBean> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "HomeHunHeBean{" +
                "lastNewsId='" + lastNewsId + '\'' +
                ", news=" + news +
                '}';
    }
}
