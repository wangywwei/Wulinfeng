package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class ChangjianWentBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : [{"id":1,"questioncol":"你好吗","answer":"我很好","url":"https://www.baidu.com"}]
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
         * questioncol : 你好吗
         * answer : 我很好
         * url : https://www.baidu.com
         */

        private int id;
        private String questioncol;
        private String answer;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestioncol() {
            return questioncol;
        }

        public void setQuestioncol(String questioncol) {
            this.questioncol = questioncol;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
