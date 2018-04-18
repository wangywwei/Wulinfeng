package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class ZixunqushiBean {

    /**
     * code : 1000
     * message : 资讯列表成功
     * data : [{"id":15,"title":"11","viewsNum":345,"newsType":3,"coverImage":"2018020216241567232805.JPG"},{"id":10,"title":"春节游玩攻略5","viewsNum":445,"newsType":3,"coverImage":"2018020118433355566491.JPG"},{"id":12,"title":"春节游玩攻略6","viewsNum":445,"newsType":3,"coverImage":"2018020118433355566491.JPG"},{"id":9,"title":"春节游玩攻略4","viewsNum":445,"newsType":3,"coverImage":"2018020118433355566491.JPG"},{"id":8,"title":"春节游玩攻略3","viewsNum":445,"newsType":3,"coverImage":"2018020118433355566491.JPG"},{"id":7,"title":"春节游玩攻略2","viewsNum":445,"newsType":3,"coverImage":"2018020118433355566491.JPG"},{"id":6,"title":"春节游玩攻略1","viewsNum":334,"newsType":3,"coverImage":"2018020118433355566491.JPG"},{"id":5,"title":"115","viewsNum":234,"newsType":1,"coverImage":"2018020117404926937353.JPG"},{"id":4,"title":"114","viewsNum":2000,"newsType":1,"coverImage":"2018020117404926937353.JPG"},{"id":3,"title":"113","viewsNum":1000,"newsType":1,"coverImage":"2018020117404926937353.JPG"}]
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
         * id : 15
         * title : 11
         * viewsNum : 345
         * newsType : 3
         * coverImage : 2018020216241567232805.JPG
         */
        private String author;
        private String imageGatherNum;
        private int id;
        private String title;
        private int viewsNum;
        private int newsType;
        private String coverImage;
        private String columnName;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getImageGatherNum() {
            return imageGatherNum;
        }

        public void setImageGatherNum(String imageGatherNum) {
            this.imageGatherNum = imageGatherNum;
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

        public int getViewsNum() {
            return viewsNum;
        }

        public void setViewsNum(int viewsNum) {
            this.viewsNum = viewsNum;
        }

        public int getNewsType() {
            return newsType;
        }

        public void setNewsType(int newsType) {
            this.newsType = newsType;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }
    }
}
