package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 * 帮助与反馈界面bean
 */
public class HelpAndReturnBean {
        private String id;
        private String title;

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

    @Override
    public String toString() {
        return "HelpAndReturnBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
