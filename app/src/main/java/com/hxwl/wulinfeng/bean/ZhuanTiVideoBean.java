package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/5.
 * 专题中视频数据结构
 */
public class ZhuanTiVideoBean {
    private String id ;
    private String type ;
    private String url ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ZhuanTiVideoBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
