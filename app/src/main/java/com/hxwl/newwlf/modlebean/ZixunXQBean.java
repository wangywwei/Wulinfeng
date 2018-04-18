package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ZixunXQBean {

    /**
     * code : 1000
     * message : 资讯详情成功
     * data : {"id":5,"scheduleId":1,"againstId":6,"agentId":6,"clubId":2,"newsType":1,"columnId":1,"title":"115","author":"11","coverImages":"2018020117404926937353.JPG","content":"<h1><br />\taa<br /><\/h1>","textContent":null,"videoUrl":null,"imageGather":null,"commentNum":null,"favourNum":null,"viewsTrue":234,"viewsFalse":0,"publishState":1,"createTime":1517479188000,"updateTime":1517479188000}
     */

    private String code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5
         * scheduleId : 1
         * againstId : 6
         * agentId : 6
         * clubId : 2
         * newsType : 1
         * columnId : 1
         * title : 115
         * author : 11
         * coverImages : 2018020117404926937353.JPG
         * content : <h1><br />	aa<br /></h1>
         * textContent : null
         * videoUrl : null
         * imageGather : null
         * commentNum : null
         * favourNum : null
         * viewsTrue : 234
         * viewsFalse : 0
         * publishState : 1
         * createTime : 1517479188000
         * updateTime : 1517479188000
         */
        private String newsUrl;

        public String getNewsUrl() {
            return newsUrl;
        }

        public void setNewsUrl(String newsUrl) {
            this.newsUrl = newsUrl;
        }

        private int id;
        private int scheduleId;
        private int againstId;
        private int agentId;
        private int clubId;
        private int newsType;
        private int columnId;
        private String title;
        private String author;
        private String coverImages;
        private String content;
        private String textContent;
        private String videoUrl;
        private String imageGather;
        private Object commentNum;
        private Object favourNum;
        private int viewsTrue;
        private int viewsFalse;
        private int publishState;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }

        public int getAgainstId() {
            return againstId;
        }

        public void setAgainstId(int againstId) {
            this.againstId = againstId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getClubId() {
            return clubId;
        }

        public void setClubId(int clubId) {
            this.clubId = clubId;
        }

        public int getNewsType() {
            return newsType;
        }

        public void setNewsType(int newsType) {
            this.newsType = newsType;
        }

        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCoverImages() {
            return coverImages;
        }

        public void setCoverImages(String coverImages) {
            this.coverImages = coverImages;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getImageGather() {
            return imageGather;
        }

        public void setImageGather(String imageGather) {
            this.imageGather = imageGather;
        }

        public Object getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(Object commentNum) {
            this.commentNum = commentNum;
        }

        public Object getFavourNum() {
            return favourNum;
        }

        public void setFavourNum(Object favourNum) {
            this.favourNum = favourNum;
        }

        public int getViewsTrue() {
            return viewsTrue;
        }

        public void setViewsTrue(int viewsTrue) {
            this.viewsTrue = viewsTrue;
        }

        public int getViewsFalse() {
            return viewsFalse;
        }

        public void setViewsFalse(int viewsFalse) {
            this.viewsFalse = viewsFalse;
        }

        public int getPublishState() {
            return publishState;
        }

        public void setPublishState(int publishState) {
            this.publishState = publishState;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
