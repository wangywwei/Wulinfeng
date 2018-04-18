package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/13.
 */
public class Zhu_info {
    private String id;

    private String contents;

    private String images;

    private String letv_video_id;

    private String letv_vu;

    private List<String> img ;

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLetv_video_id() {
        return letv_video_id;
    }

    public void setLetv_video_id(String letv_video_id) {
        this.letv_video_id = letv_video_id;
    }

    public String getLetv_vu() {
        return letv_vu;
    }

    public void setLetv_vu(String letv_vu) {
        this.letv_vu = letv_vu;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Zhu_info{" +
                "id='" + id + '\'' +
                ", contents='" + contents + '\'' +
                ", images='" + images + '\'' +
                ", letv_video_id='" + letv_video_id + '\'' +
                ", letv_vu='" + letv_vu + '\'' +
                ", img=" + img +
                '}';
    }
}
