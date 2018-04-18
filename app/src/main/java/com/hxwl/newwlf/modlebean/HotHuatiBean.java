package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 */

public class HotHuatiBean {

    /**
     * code : 1000
     * message : 热门话题成功
     * data : [{"id":12,"topicName":"话题9","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":345,"falseJoinNum":6,"updateTime":1517825497000,"isRecommend":1,"rank":52566},{"id":7,"topicName":"话题4","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":456,"falseJoinNum":6,"updateTime":1517825497000,"isRecommend":1,"rank":5534},{"id":11,"topicName":"话题8","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":23566,"falseJoinNum":6,"updateTime":1517825497000,"isRecommend":1,"rank":673},{"id":13,"topicName":"话题10","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":7,"falseJoinNum":6,"updateTime":1517825497000,"isRecommend":1,"rank":255},{"id":10,"topicName":"话题7","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":4356,"falseJoinNum":6,"updateTime":1517825497000,"isRecommend":1,"rank":67},{"id":8,"topicName":"话题5","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":25,"falseJoinNum":6,"updateTime":1517825497000,"isRecommend":1,"rank":45}]
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
         * id : 12
         * topicName : 话题9
         * content : 话题一的内容
         * image : 2018020518112742993172.JPG
         * state : null
         * referNum : null
         * joinNum : 345
         * falseJoinNum : 6
         * updateTime : 1517825497000
         * isRecommend : 1
         * rank : 52566
         */

        private int id;
        private String topicName;
        private String content;
        private String image;
        private Object state;
        private Object referNum;
        private int joinNum;
        private int falseJoinNum;
        private long updateTime;
        private int isRecommend;
        private int rank;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getReferNum() {
            return referNum;
        }

        public void setReferNum(Object referNum) {
            this.referNum = referNum;
        }

        public int getJoinNum() {
            return joinNum;
        }

        public void setJoinNum(int joinNum) {
            this.joinNum = joinNum;
        }

        public int getFalseJoinNum() {
            return falseJoinNum;
        }

        public void setFalseJoinNum(int falseJoinNum) {
            this.falseJoinNum = falseJoinNum;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
