package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/28.
 */
public class ZhuanTiDetailPackBean {
    private String lastNewsId ;
    private List<ZhuanTiDetailBean> news ;

    public String getLastNewsId() {
        return lastNewsId;
    }

    public void setLastNewsId(String lastNewsId) {
        this.lastNewsId = lastNewsId;
    }

    public List<ZhuanTiDetailBean> getNews() {
        return news;
    }

    public void setNews(List<ZhuanTiDetailBean> news) {
        this.news = news;
    }
}
