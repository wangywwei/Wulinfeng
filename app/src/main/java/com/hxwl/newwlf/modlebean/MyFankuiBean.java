package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MyFankuiBean {

    /**
     * code : 1000
     * message : 反馈列表
     * data : [{"id":3,"userId":1,"content":"123123","contact":"showadmin","answer":null,"adminId":null,"updateTime":1519635803000},{"id":2,"userId":1,"content":"123123","contact":"showadmin","answer":null,"adminId":null,"updateTime":1519635790000},{"id":1,"userId":1,"content":"123123","contact":"public","answer":null,"adminId":null,"updateTime":1519635719000}]
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
         * id : 3
         * userId : 1
         * content : 123123
         * contact : showadmin
         * answer : null
         * adminId : null
         * updateTime : 1519635803000
         */

        private int id;
        private int userId;
        private String content;
        private String contact;
        private String answer;
        private String adminId;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
