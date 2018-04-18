package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/7/11.
 */
public class BannerListBean {
    private String id ;
    private String type ;
    private String jump_type;
    private String saishiId;
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getJump_type() {
        return jump_type;
    }

    public void setJump_type(String jump_type) {
        this.jump_type = jump_type;
    }

    public String getsaishiId() {
        return saishiId;
    }

    public String getSaishiId() {
        return saishiId;
    }

    public void setSaishiId(String saishiId) {
        this.saishiId = saishiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
