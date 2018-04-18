package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class GenHuatiBean {

    /**
     * code : 1000
     * message : 话题列表成功
     * data : {"topicList":[{"id":4,"topicName":"话题一","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":234240,"updateTime":1517825497000,"isRecommend":2,"rank":22},{"id":1,"topicName":"一龙专题","content":"一龙是中国最强大的拳手","image":"2018020518112742993172.JPG","state":null,"referNum":234,"joinNum":236600,"updateTime":1517803805000,"isRecommend":2,"rank":null},{"id":2,"topicName":"天子专题","content":"你好要地要地和的有有朋","image":"2018020518112742993172.JPG","state":null,"referNum":23566,"joinNum":901,"updateTime":1515471035000,"isRecommend":2,"rank":null},{"id":3,"topicName":"海子专题","content":"你地地酸辣粉地要地地","image":"2018020518112742993172.JPG","state":null,"referNum":23566,"joinNum":901,"updateTime":1515039035000,"isRecommend":2,"rank":null}],"hotTopicList":[{"id":12,"topicName":"话题9","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":351,"updateTime":1517825497000,"isRecommend":1,"rank":52566},{"id":7,"topicName":"话题4","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":462,"updateTime":1517825497000,"isRecommend":1,"rank":5534},{"id":11,"topicName":"话题8","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":23572,"updateTime":1517825497000,"isRecommend":1,"rank":673},{"id":13,"topicName":"话题10","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":13,"updateTime":1517825497000,"isRecommend":1,"rank":255},{"id":10,"topicName":"话题7","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":4362,"updateTime":1517825497000,"isRecommend":1,"rank":67},{"id":8,"topicName":"话题5","content":"话题一的内容","image":"2018020518112742993172.JPG","state":null,"referNum":null,"joinNum":31,"updateTime":1517825497000,"isRecommend":1,"rank":45}]}
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
        private List<TopicListBean> topicList;
        private List<HotTopicListBean> hotTopicList;

        public List<TopicListBean> getTopicList() {
            return topicList;
        }

        public void setTopicList(List<TopicListBean> topicList) {
            this.topicList = topicList;
        }

        public List<HotTopicListBean> getHotTopicList() {
            return hotTopicList;
        }

        public void setHotTopicList(List<HotTopicListBean> hotTopicList) {
            this.hotTopicList = hotTopicList;
        }

        public static class TopicListBean implements Serializable{
            /**
             * id : 4
             * topicName : 话题一
             * content : 话题一的内容
             * image : 2018020518112742993172.JPG
             * state : null
             * referNum : null
             * joinNum : 234240
             * updateTime : 1517825497000
             * isRecommend : 2
             * rank : 22
             */

            private int id;
            private String topicName;
            private String content;
            private String image;
            private Object state;
            private Object referNum;
            private int joinNum;
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

        public static class HotTopicListBean implements Serializable{
            /**
             * id : 12
             * topicName : 话题9
             * content : 话题一的内容
             * image : 2018020518112742993172.JPG
             * state : null
             * referNum : null
             * joinNum : 351
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
}
