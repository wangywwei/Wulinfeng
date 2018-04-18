package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/5.
 * 专题全部信息
 */
public class AllZhuanTiBean {
    private String id ;
    private String img ;
    private String title ;
    private String label ;
    private String type_;

    public String getType_() {
        return type_;
    }

    public void setType_(String type_) {
        this.type_ = type_;
    }


    private List<ZhuanTiVideoBean> row ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public List<ZhuanTiVideoBean> getRow() {
        return row;
    }

    public void setRow(List<ZhuanTiVideoBean> row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "AllZhuanTiBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", row=" + row +
                '}';
    }
}
