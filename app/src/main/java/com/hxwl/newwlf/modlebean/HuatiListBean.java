package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class HuatiListBean implements Serializable{

    /**
     * code : 1000
     * message : 话题列表成功
     * data : [{"id":1,"topicName":"一龙专题","content":"一龙是中国最强大的拳手","image":"23423423.jpg","state":null,"referNum":234,"joinNum":2366,"faseJoinNum":234234,"updateTime":1517803805000},{"id":2,"topicName":"天子专题","content":"你好要地要地和的有有朋","image":"23423423.jpg","state":null,"referNum":23566,"joinNum":677,"faseJoinNum":224,"updateTime":1515471035000},{"id":3,"topicName":"海子专题","content":"你地地酸辣粉地要地地","image":"23423423.jpg","state":null,"referNum":23566,"joinNum":677,"faseJoinNum":224,"updateTime":1515039035000}]
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
         * id : 1
         * topicName : 一龙专题
         * content : 一龙是中国最强大的拳手
         * image : 23423423.jpg
         * state : null
         * referNum : 234
         * joinNum : 2366
         * faseJoinNum : 234234
         * updateTime : 1517803805000
         */

        private int id;
        private String topicName;
        private String content;
        private String image;
        private Object state;
        private int referNum;
        private int joinNum;
        private int faseJoinNum;
        private long updateTime;

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

        public int getReferNum() {
            return referNum;
        }

        public void setReferNum(int referNum) {
            this.referNum = referNum;
        }

        public int getJoinNum() {
            return joinNum;
        }

        public void setJoinNum(int joinNum) {
            this.joinNum = joinNum;
        }

        public int getFaseJoinNum() {
            return faseJoinNum;
        }

        public void setFaseJoinNum(int faseJoinNum) {
            this.faseJoinNum = faseJoinNum;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
