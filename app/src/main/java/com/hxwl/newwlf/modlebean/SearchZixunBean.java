package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class SearchZixunBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : [{"viewsTrue":445,"author":"牛人","viewsFalse":"100","id":"7","title":"春节游玩攻略2","newsType":"3","coverImages":"2018020118433355566491.JPG"}]
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
         * viewsTrue : 445
         * author : 牛人
         * viewsFalse : 100
         * id : 7
         * title : 春节游玩攻略2
         * newsType : 3
         * coverImages : 2018020118433355566491.JPG
         */

        private int viewsTrue;
        private String author;
        private String viewsFalse;
        private String id;
        private String title;
        private String newsType;
        private String coverImages;
        private String imageGatherNum;

        public String getImageGatherNum() {
            return imageGatherNum;
        }

        public void setImageGatherNum(String imageGatherNum) {
            this.imageGatherNum = imageGatherNum;
        }

        public int getViewsTrue() {
            return viewsTrue;
        }

        public void setViewsTrue(int viewsTrue) {
            this.viewsTrue = viewsTrue;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getViewsFalse() {
            return viewsFalse;
        }

        public void setViewsFalse(String viewsFalse) {
            this.viewsFalse = viewsFalse;
        }

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

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }

        public String getCoverImages() {
            return coverImages;
        }

        public void setCoverImages(String coverImages) {
            this.coverImages = coverImages;
        }
    }
}
