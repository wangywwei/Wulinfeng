package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/3/10.
 */

public class HomeRedBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : {"hasNew":false}
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
         * hasNew : false
         */

        private boolean hasNew;

        public boolean isHasNew() {
            return hasNew;
        }

        public void setHasNew(boolean hasNew) {
            this.hasNew = hasNew;
        }
    }
}
