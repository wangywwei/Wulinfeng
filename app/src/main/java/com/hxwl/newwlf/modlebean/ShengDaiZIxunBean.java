package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class ShengDaiZIxunBean {

    /**
     * code : 1000
     * message : 省代资讯列表
     * data : [{"viewNum":101,"author":"lls","id":40,"title":"龙长很牛","newsType":1,"columnName":"视频"},{"viewNum":1022,"author":"11","id":3,"title":"113","newsType":1,"columnName":"视频"},{"viewNum":181,"author":"11","id":39,"title":"14","newsType":1,"columnName":"视频"},{"viewNum":34,"author":"11","id":38,"title":"13","newsType":1,"columnName":"视频"},{"viewNum":14,"author":"11","id":37,"title":"12","newsType":1,"columnName":"视频"},{"viewNum":29,"author":"an","id":33,"title":"xinwen1","newsType":1,"columnName":"视频"},{"viewNum":3701,"author":"中国搏击","id":32,"title":"拳击宝贝梅威瑟性场不惧","newsType":3,"columnName":"视频"},{"viewNum":3659,"author":"中国搏击","id":30,"title":"拳击宝贝性场不惧","newsType":3,"columnName":"视频"},{"viewNum":3654,"author":"中国搏击","id":29,"title":"拳击宝贝性感热舞爆乳惊魂 卖力暖场不惧走光","newsType":3,"columnName":"视频"},{"viewNum":3658,"author":"中国搏击","id":27,"title":"手来说早已经到了退役的年龄","newsType":3,"columnName":"视频"}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * viewNum : 101
         * author : lls
         * id : 40
         * title : 龙长很牛
         * newsType : 1
         * columnName : 视频
         */

        private int viewNum;
        private String author;
        private int id;
        private String title;
        private int newsType;
        private String columnName;
        private String imageGather;
        private String coverImages;

        public String getCoverImages() {
            return coverImages;
        }

        public void setCoverImages(String coverImages) {
            this.coverImages = coverImages;
        }

        public String getImageGather() {
            return imageGather;
        }

        public void setImageGather(String imageGather) {
            this.imageGather = imageGather;
        }

        public int getViewNum() {
            return viewNum;
        }

        public void setViewNum(int viewNum) {
            this.viewNum = viewNum;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNewsType() {
            return newsType;
        }

        public void setNewsType(int newsType) {
            this.newsType = newsType;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }
}
