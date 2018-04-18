package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/6.
 * 图集 每张图片的详情
 */
public class TujiImgs {
    private String id;

    private String contents;

    private String url;

    private String img_list_id;

    private String is_show;

    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg_list_id() {
        return img_list_id;
    }

    public void setImg_list_id(String img_list_id) {
        this.img_list_id = img_list_id;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TujiImgs{" +
                "id='" + id + '\'' +
                ", contents='" + contents + '\'' +
                ", url='" + url + '\'' +
                ", img_list_id='" + img_list_id + '\'' +
                ", is_show='" + is_show + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
