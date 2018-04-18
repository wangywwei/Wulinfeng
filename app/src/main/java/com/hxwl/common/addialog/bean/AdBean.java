package com.hxwl.common.addialog.bean;

/**
 * 功能:广告的实体信息
 * 作者：zjn on 2017/4/24 10:26
 */

public class AdBean {
    //广告id
    private String adId = null;
    //标题
    private String title = null;
    //跳转链接
    private String url = null;
    //广告图片URL
    private String imgUrl = null;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
