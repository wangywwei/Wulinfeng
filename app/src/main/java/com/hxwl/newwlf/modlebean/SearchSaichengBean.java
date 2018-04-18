package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class SearchSaichengBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : [{"scheduleBeginTime":"1516069542000","holdAddress":"郑州市万豪酒店","scheduleName":"要地地相辅相成要9","hasSubscribe":false,"publicityImg":"2018012202.png","state":"1","scheduleId":"1","scheduleEndTime":"1517381483000"},{"scheduleBeginTime":"1516774242000","holdAddress":"郑州市sl豪酒店","scheduleName":"刀光和要2","hasSubscribe":true,"publicityImg":"4123454555.png","state":"2","scheduleId":"5","scheduleEndTime":"1516774248000"}]
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
         * scheduleBeginTime : 1516069542000
         * holdAddress : 郑州市万豪酒店
         * scheduleName : 要地地相辅相成要9
         * hasSubscribe : false
         * publicityImg : 2018012202.png
         * state : 1
         * scheduleId : 1
         * scheduleEndTime : 1517381483000
         */

        private long scheduleBeginTime;
        private String holdAddress;
        private String scheduleName;
        private boolean hasSubscribe;
        private String publicityImg;
        private String state;
        private String scheduleId;
        private String scheduleEndTime;

        public long getScheduleBeginTime() {
            return scheduleBeginTime;
        }

        public void setScheduleBeginTime(long scheduleBeginTime) {
            this.scheduleBeginTime = scheduleBeginTime;
        }

        public String getHoldAddress() {
            return holdAddress;
        }

        public void setHoldAddress(String holdAddress) {
            this.holdAddress = holdAddress;
        }

        public String getScheduleName() {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public boolean isHasSubscribe() {
            return hasSubscribe;
        }

        public void setHasSubscribe(boolean hasSubscribe) {
            this.hasSubscribe = hasSubscribe;
        }

        public String getPublicityImg() {
            return publicityImg;
        }

        public void setPublicityImg(String publicityImg) {
            this.publicityImg = publicityImg;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getScheduleEndTime() {
            return scheduleEndTime;
        }

        public void setScheduleEndTime(String scheduleEndTime) {
            this.scheduleEndTime = scheduleEndTime;
        }
    }
}
