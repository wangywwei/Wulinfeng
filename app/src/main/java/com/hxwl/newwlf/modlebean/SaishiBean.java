package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/13.
 */

public class SaishiBean {

    /**
     * code : 1000
     * message : 获取用户预约赛程
     * data : [{"scheduleName":"风清月明1","holdAddress":"郑州市sl豪酒店","scheduleState":2,"competitionName":"武林风1","publicityImg":"4123454555.png","updateTime":1516773776000,"scheduleId":3},null,{"scheduleName":"刀光和要2","holdAddress":"郑州市sl豪酒店","scheduleState":2,"competitionName":"武林风1","publicityImg":"4123454555.png","updateTime":1516774253000,"scheduleId":5},{"scheduleName":"赛事2对阵24","holdAddress":"郑州市sl豪酒店","scheduleState":3,"competitionName":"武林风2","publicityImg":"4123454555.png","updateTime":1516774316000,"scheduleId":8}]
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
         * scheduleName : 风清月明1
         * holdAddress : 郑州市sl豪酒店
         * scheduleState : 2
         * competitionName : 武林风1
         * publicityImg : 4123454555.png
         * updateTime : 1516773776000
         * scheduleId : 3
         */

        private String scheduleName;
        private String holdAddress;
        private int scheduleState;
        private String competitionName;
        private String publicityImg;
        private long updateTime;
        private int scheduleId;
        private boolean yuyue;

        public boolean isYuyue() {
            return yuyue;
        }

        public void setYuyue(boolean yuyue) {
            this.yuyue = yuyue;
        }

        public String getScheduleName() {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public String getHoldAddress() {
            return holdAddress;
        }

        public void setHoldAddress(String holdAddress) {
            this.holdAddress = holdAddress;
        }

        public int getScheduleState() {
            return scheduleState;
        }

        public void setScheduleState(int scheduleState) {
            this.scheduleState = scheduleState;
        }

        public String getCompetitionName() {
            return competitionName;
        }

        public void setCompetitionName(String competitionName) {
            this.competitionName = competitionName;
        }

        public String getPublicityImg() {
            return publicityImg;
        }

        public void setPublicityImg(String publicityImg) {
            this.publicityImg = publicityImg;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }
    }
}
