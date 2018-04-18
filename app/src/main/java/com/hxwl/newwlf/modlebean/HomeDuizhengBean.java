package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class HomeDuizhengBean {

    /**
     * code : 1000
     * message : 获取赛程对阵列表
     * data : [{"scheduleId":3,"scheduleName":"天涯要有产有要你好吗","againstName":"五龙对阵六龙","scheduleBeginTime":1516531851000,"scheduleState":3,"redPlayerId":1,"redPlayerName":"一龙","redPlayerHeadImage":"20181112.png","redPlayerClubName":"飞虎俱乐部","bluePlayerId":2,"bluePlayerName":"二龙","bluePlayerHeadImage":"20181113.png","bluePlayerClubName":"飞虎俱乐部","sessionNum":1},{"scheduleId":3,"scheduleName":"天涯要有产有要你好吗","againstName":"七龙对阵8龙","scheduleBeginTime":1516531851000,"scheduleState":3,"redPlayerId":1,"redPlayerName":"一龙","redPlayerHeadImage":"20181112.png","redPlayerClubName":"飞虎俱乐部","bluePlayerId":2,"bluePlayerName":"二龙","bluePlayerHeadImage":"20181113.png","bluePlayerClubName":"飞虎俱乐部","sessionNum":2},{"scheduleId":3,"scheduleName":"天涯要有产有要你好吗","againstName":"9龙对阵10龙","scheduleBeginTime":1516531851000,"scheduleState":3,"redPlayerId":1,"redPlayerName":"一龙","redPlayerHeadImage":"20181112.png","redPlayerClubName":"飞虎俱乐部","bluePlayerId":2,"bluePlayerName":"二龙","bluePlayerHeadImage":"20181113.png","bluePlayerClubName":"飞虎俱乐部","sessionNum":3}]
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
         * scheduleId : 3
         * scheduleName : 天涯要有产有要你好吗
         * againstName : 五龙对阵六龙
         * scheduleBeginTime : 1516531851000
         * scheduleState : 3
         * redPlayerId : 1
         * redPlayerName : 一龙
         * redPlayerHeadImage : 20181112.png
         * redPlayerClubName : 飞虎俱乐部
         * bluePlayerId : 2
         * bluePlayerName : 二龙
         * bluePlayerHeadImage : 20181113.png
         * bluePlayerClubName : 飞虎俱乐部
         * sessionNum : 1
         */

        private int scheduleId;
        private String scheduleName;
        private String againstName;
        private long scheduleBeginTime;
        private int scheduleState;
        private int redPlayerId;
        private String redPlayerName;
        private String redPlayerHeadImage;
        private String redPlayerClubName;
        private int bluePlayerId;
        private String bluePlayerName;
        private String bluePlayerHeadImage;
        private String bluePlayerClubName;
        private int sessionNum;

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getScheduleName() {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public String getAgainstName() {
            return againstName;
        }

        public void setAgainstName(String againstName) {
            this.againstName = againstName;
        }

        public long getScheduleBeginTime() {
            return scheduleBeginTime;
        }

        public void setScheduleBeginTime(long scheduleBeginTime) {
            this.scheduleBeginTime = scheduleBeginTime;
        }

        public int getScheduleState() {
            return scheduleState;
        }

        public void setScheduleState(int scheduleState) {
            this.scheduleState = scheduleState;
        }

        public int getRedPlayerId() {
            return redPlayerId;
        }

        public void setRedPlayerId(int redPlayerId) {
            this.redPlayerId = redPlayerId;
        }

        public String getRedPlayerName() {
            return redPlayerName;
        }

        public void setRedPlayerName(String redPlayerName) {
            this.redPlayerName = redPlayerName;
        }

        public String getRedPlayerHeadImage() {
            return redPlayerHeadImage;
        }

        public void setRedPlayerHeadImage(String redPlayerHeadImage) {
            this.redPlayerHeadImage = redPlayerHeadImage;
        }

        public String getRedPlayerClubName() {
            return redPlayerClubName;
        }

        public void setRedPlayerClubName(String redPlayerClubName) {
            this.redPlayerClubName = redPlayerClubName;
        }

        public int getBluePlayerId() {
            return bluePlayerId;
        }

        public void setBluePlayerId(int bluePlayerId) {
            this.bluePlayerId = bluePlayerId;
        }

        public String getBluePlayerName() {
            return bluePlayerName;
        }

        public void setBluePlayerName(String bluePlayerName) {
            this.bluePlayerName = bluePlayerName;
        }

        public String getBluePlayerHeadImage() {
            return bluePlayerHeadImage;
        }

        public void setBluePlayerHeadImage(String bluePlayerHeadImage) {
            this.bluePlayerHeadImage = bluePlayerHeadImage;
        }

        public String getBluePlayerClubName() {
            return bluePlayerClubName;
        }

        public void setBluePlayerClubName(String bluePlayerClubName) {
            this.bluePlayerClubName = bluePlayerClubName;
        }

        public int getSessionNum() {
            return sessionNum;
        }

        public void setSessionNum(int sessionNum) {
            this.sessionNum = sessionNum;
        }
    }
}
