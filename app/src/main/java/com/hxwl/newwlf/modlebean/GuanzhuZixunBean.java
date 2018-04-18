package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/12.
 */

public class GuanzhuZixunBean {

    /**
     * code : 1000
     * message : 获取资讯列表根据选手id
     * data : [{"id":20,"imageGatherNum":0,"author":"格斗媒体","title":"根据世界权威格斗媒体CombatPress公布的最新榜单显示，方便在中量级排名世界第八，较上个月下滑了一位，而在两年前惨败在方便拳下的加拿大肌肉狂人\u201c坏小子\u201d西","viewsNum":0,"newsType":1,"coverImage":"2018021014233187325971.jpg"},{"id":18,"imageGatherNum":0,"author":"111","title":"111","viewsNum":0,"newsType":1,"coverImage":"2018021013531152097605.JPG"},{"id":16,"imageGatherNum":3,"author":"民间大师","title":"《武林风》是各种民间大师们展现娱乐节目的第一品牌，雄霸河南卫视周六晚间黄金时段，时长90分钟，全方","imageGather":"2018021011530106879918.jpg,2018021011530457955754.jpg,2018021011531380185883.jpg","viewsNum":10000,"newsType":2,"coverImage":"2018021011523543782128.jpg"},{"id":15,"imageGatherNum":0,"author":"11","title":"11","viewsNum":345,"newsType":3,"coverImage":"2018020216241567232805.JPG"},{"id":1,"imageGatherNum":2,"author":"11","title":"新春到，快来到","imageGather":"2018020117480855531029.JPG,2018020117490370055384.JPG","viewsNum":100,"newsType":2,"coverImage":"2018020117404926937353.JPG"}]
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
         * id : 20
         * imageGatherNum : 0
         * author : 格斗媒体
         * title : 根据世界权威格斗媒体CombatPress公布的最新榜单显示，方便在中量级排名世界第八，较上个月下滑了一位，而在两年前惨败在方便拳下的加拿大肌肉狂人“坏小子”西
         * viewsNum : 0
         * newsType : 1
         * coverImage : 2018021014233187325971.jpg
         * imageGather : 2018021011530106879918.jpg,2018021011530457955754.jpg,2018021011531380185883.jpg
         */

        private int id;
        private int imageGatherNum;
        private String author;
        private String title;
        private int viewsNum;
        private int newsType;
        private String coverImage;
        private String imageGather;
        private String columnName;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getImageGatherNum() {
            return imageGatherNum;
        }

        public void setImageGatherNum(int imageGatherNum) {
            this.imageGatherNum = imageGatherNum;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public String getImageGather() {
            return imageGather;
        }

        public void setImageGather(String imageGather) {
            this.imageGather = imageGather;
        }
    }
}
