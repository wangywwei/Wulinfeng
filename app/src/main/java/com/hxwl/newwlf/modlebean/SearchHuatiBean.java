package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class SearchHuatiBean {


    /**
     * code : 1000
     * message : 请求成功
     * data : [{"joinNum":236600,"falseJoinNum":"234234","topicName":"一龙专题","id":"1","content":"一龙是中国最强大的拳手"}]
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
         * joinNum : 236600
         * falseJoinNum : 234234
         * topicName : 一龙专题
         * id : 1
         * content : 一龙是中国最强大的拳手
         */

        private int joinNum;
        private String falseJoinNum;
        private String topicName;
        private String id;
        private String content;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getJoinNum() {
            return joinNum;
        }

        public void setJoinNum(int joinNum) {
            this.joinNum = joinNum;
        }

        public String getFalseJoinNum() {
            return falseJoinNum;
        }

        public void setFalseJoinNum(String falseJoinNum) {
            this.falseJoinNum = falseJoinNum;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
