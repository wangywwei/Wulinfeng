package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DuizhengXQBean {

    /**
     * code : 1000
     * message : 对阵详情成功
     * data : {"id":1,"againstName":"北京赛第一场","competitionId":1,"scheduleId":2,"sessionNum":1,"redPlayerId":13,"bluePlayerId":58,"result":0,"winWay":0,"isStatisticsStandings":1,"remark":"第一场，一龙上，凑够十个字","videoId":1,"updateTime":1520918871000,"type":null}
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
         * id : 1
         * againstName : 北京赛第一场
         * competitionId : 1
         * scheduleId : 2
         * sessionNum : 1
         * redPlayerId : 13
         * bluePlayerId : 58
         * result : 0
         * winWay : 0
         * isStatisticsStandings : 1
         * remark : 第一场，一龙上，凑够十个字
         * videoId : 1
         * updateTime : 1520918871000
         * type : null
         */

        private int id;
        private String againstName;
        private int competitionId;
        private int scheduleId;
        private int sessionNum;
        private int redPlayerId;
        private int bluePlayerId;
        private int result;
        private int winWay;
        private int isStatisticsStandings;
        private String remark;
        private int videoId;
        private long updateTime;
        private String videoUrl;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        private Object type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAgainstName() {
            return againstName;
        }

        public void setAgainstName(String againstName) {
            this.againstName = againstName;
        }

        public int getCompetitionId() {
            return competitionId;
        }

        public void setCompetitionId(int competitionId) {
            this.competitionId = competitionId;
        }

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }

        public int getSessionNum() {
            return sessionNum;
        }

        public void setSessionNum(int sessionNum) {
            this.sessionNum = sessionNum;
        }

        public int getRedPlayerId() {
            return redPlayerId;
        }

        public void setRedPlayerId(int redPlayerId) {
            this.redPlayerId = redPlayerId;
        }

        public int getBluePlayerId() {
            return bluePlayerId;
        }

        public void setBluePlayerId(int bluePlayerId) {
            this.bluePlayerId = bluePlayerId;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getWinWay() {
            return winWay;
        }

        public void setWinWay(int winWay) {
            this.winWay = winWay;
        }

        public int getIsStatisticsStandings() {
            return isStatisticsStandings;
        }

        public void setIsStatisticsStandings(int isStatisticsStandings) {
            this.isStatisticsStandings = isStatisticsStandings;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }
    }
}
