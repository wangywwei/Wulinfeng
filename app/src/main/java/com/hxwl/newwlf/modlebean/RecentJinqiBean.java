package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class RecentJinqiBean {

    /**
     * code : 1000
     * message : 通过赛程id获取对阵列表
     * data : {"notBeginScheduleList":[{"competitionId":1,"competitionName":"武林风1","scheduleId":1,"scheduleName":"要地地相辅相成要9","scheduleBeginTime":1516069542000,"scheduleState":1,"publicityImg":"2018012202.png","holdAddress":"郑州市万豪酒店","userIsSubscribe":"0","againstInfoList":null},{"competitionId":1,"competitionName":"武林风1","scheduleId":2,"scheduleName":"互相要地有物相成地10","scheduleBeginTime":1516776987000,"scheduleState":1,"publicityImg":"2018012301.png","holdAddress":"郑州市gd豪酒店","userIsSubscribe":"0","againstInfoList":null},{"competitionId":1,"competitionName":"武林风1","scheduleId":13,"scheduleName":"赛1","scheduleBeginTime":1516809600000,"scheduleState":1,"publicityImg":"2018012518494168470075.png","holdAddress":"2wew","userIsSubscribe":"0","againstInfoList":null},{"competitionId":1,"competitionName":"武林风1","scheduleId":14,"scheduleName":"赛2","scheduleBeginTime":1516809600000,"scheduleState":1,"publicityImg":"2018012518494168470075.png","holdAddress":"2wew","userIsSubscribe":"0","againstInfoList":null},{"competitionId":1,"competitionName":"武林风1","scheduleId":15,"scheduleName":"赛3","scheduleBeginTime":1509465600000,"scheduleState":1,"publicityImg":"2018012518494168470075.png","holdAddress":"2wew","userIsSubscribe":"0","againstInfoList":null},{"competitionId":1,"competitionName":"武林风1","scheduleId":16,"scheduleName":"赛4","scheduleBeginTime":1512059400000,"scheduleState":1,"publicityImg":"2018012610060322936406.png","holdAddress":"1212","userIsSubscribe":"0","againstInfoList":null}],"liveScheduleList":[{"competitionId":1,"competitionName":"武林风1","scheduleId":3,"scheduleName":"风清月明1","scheduleBeginTime":1516674022000,"scheduleState":2,"publicityImg":"4123454555.png","holdAddress":"郑州市sl豪酒店","userIsSubscribe":null,"againstInfoList":null},{"competitionId":1,"competitionName":"武林风1","scheduleId":5,"scheduleName":"刀光和要2","scheduleBeginTime":1516774242000,"scheduleState":2,"publicityImg":"4123454555.png","holdAddress":"郑州市sl豪酒店","userIsSubscribe":null,"againstInfoList":null}]}
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
        private List<NotBeginScheduleListBean> notBeginScheduleList;
        private List<LiveScheduleListBean> liveScheduleList;

        public List<NotBeginScheduleListBean> getNotBeginScheduleList() {
            return notBeginScheduleList;
        }

        public void setNotBeginScheduleList(List<NotBeginScheduleListBean> notBeginScheduleList) {
            this.notBeginScheduleList = notBeginScheduleList;
        }

        public List<LiveScheduleListBean> getLiveScheduleList() {
            return liveScheduleList;
        }

        public void setLiveScheduleList(List<LiveScheduleListBean> liveScheduleList) {
            this.liveScheduleList = liveScheduleList;
        }

        public static class NotBeginScheduleListBean {
            /**
             * competitionId : 1
             * competitionName : 武林风1
             * scheduleId : 1
             * scheduleName : 要地地相辅相成要9
             * scheduleBeginTime : 1516069542000
             * scheduleState : 1
             * publicityImg : 2018012202.png
             * holdAddress : 郑州市万豪酒店
             * userIsSubscribe : 0
             * againstInfoList : null
             */

            private int competitionId;
            private String competitionName;
            private int scheduleId;
            private String scheduleName;
            private long scheduleBeginTime;
            private int scheduleState;
            private String publicityImg;
            private String holdAddress;
            private String userIsSubscribe;
            private Object againstInfoList;

            public int getCompetitionId() {
                return competitionId;
            }

            public void setCompetitionId(int competitionId) {
                this.competitionId = competitionId;
            }

            public String getCompetitionName() {
                return competitionName;
            }

            public void setCompetitionName(String competitionName) {
                this.competitionName = competitionName;
            }

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

            public String getPublicityImg() {
                return publicityImg;
            }

            public void setPublicityImg(String publicityImg) {
                this.publicityImg = publicityImg;
            }

            public String getHoldAddress() {
                return holdAddress;
            }

            public void setHoldAddress(String holdAddress) {
                this.holdAddress = holdAddress;
            }

            public String getUserIsSubscribe() {
                return userIsSubscribe;
            }

            public void setUserIsSubscribe(String userIsSubscribe) {
                this.userIsSubscribe = userIsSubscribe;
            }

            public Object getAgainstInfoList() {
                return againstInfoList;
            }

            public void setAgainstInfoList(Object againstInfoList) {
                this.againstInfoList = againstInfoList;
            }
        }

        public static class LiveScheduleListBean {
            /**
             * competitionId : 1
             * competitionName : 武林风1
             * scheduleId : 3
             * scheduleName : 风清月明1
             * scheduleBeginTime : 1516674022000
             * scheduleState : 2
             * publicityImg : 4123454555.png
             * holdAddress : 郑州市sl豪酒店
             * userIsSubscribe : null
             * againstInfoList : null
             */

            private int competitionId;
            private String competitionName;
            private int scheduleId;
            private String scheduleName;
            private long scheduleBeginTime;
            private int scheduleState;
            private String publicityImg;
            private String holdAddress;
            private Object userIsSubscribe;
            private Object againstInfoList;

            public int getCompetitionId() {
                return competitionId;
            }

            public void setCompetitionId(int competitionId) {
                this.competitionId = competitionId;
            }

            public String getCompetitionName() {
                return competitionName;
            }

            public void setCompetitionName(String competitionName) {
                this.competitionName = competitionName;
            }

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

            public String getPublicityImg() {
                return publicityImg;
            }

            public void setPublicityImg(String publicityImg) {
                this.publicityImg = publicityImg;
            }

            public String getHoldAddress() {
                return holdAddress;
            }

            public void setHoldAddress(String holdAddress) {
                this.holdAddress = holdAddress;
            }

            public Object getUserIsSubscribe() {
                return userIsSubscribe;
            }

            public void setUserIsSubscribe(Object userIsSubscribe) {
                this.userIsSubscribe = userIsSubscribe;
            }

            public Object getAgainstInfoList() {
                return againstInfoList;
            }

            public void setAgainstInfoList(Object againstInfoList) {
                this.againstInfoList = againstInfoList;
            }
        }
    }
}
