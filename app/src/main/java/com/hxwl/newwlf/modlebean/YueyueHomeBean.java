package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class YueyueHomeBean {

    /**
     * code : null
     * message : null
     * data : {"subscribeStatusList":[{"hasSubscribe":false,"userId":1,"scheduleId":2},{"hasSubscribe":false,"userId":1,"scheduleId":3},{"hasSubscribe":false,"userId":1,"scheduleId":7}]}
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
        private List<SubscribeStatusListBean> subscribeStatusList;

        public List<SubscribeStatusListBean> getSubscribeStatusList() {
            return subscribeStatusList;
        }

        public void setSubscribeStatusList(List<SubscribeStatusListBean> subscribeStatusList) {
            this.subscribeStatusList = subscribeStatusList;
        }

        public static class SubscribeStatusListBean {
            /**
             * hasSubscribe : false
             * userId : 1
             * scheduleId : 2
             */

            private boolean hasSubscribe;
            private int userId;
            private int scheduleId;

            public boolean isHasSubscribe() {
                return hasSubscribe;
            }

            public void setHasSubscribe(boolean hasSubscribe) {
                this.hasSubscribe = hasSubscribe;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getScheduleId() {
                return scheduleId;
            }

            public void setScheduleId(int scheduleId) {
                this.scheduleId = scheduleId;
            }
        }
    }
}
