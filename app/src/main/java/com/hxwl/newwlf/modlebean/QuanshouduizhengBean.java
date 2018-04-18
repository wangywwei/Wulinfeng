package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class QuanshouduizhengBean {

    /**
     * code : 1000
     * message : 获取选手对阵列表
     * data : [{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"五龙对阵六龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"一龙对阵二龙","redPlayerId":3,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"3龙","againstResultState":2,"againstResult":"胜"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":3,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"3龙","againstResultState":2,"againstResult":"胜"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":3,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"3龙","againstResultState":2,"againstResult":"胜"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"七龙对阵8龙","redPlayerId":1,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"三龙对阵四龙","redPlayerId":3,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"3龙","againstResultState":1,"againstResult":"负"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":3,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"3龙","againstResultState":2,"againstResult":"胜"},{"scheduleName":"疯狂的石头3","beginTime":1516774300000,"scheduleState":3,"againstName":"9龙对阵10龙","redPlayerId":3,"bluePlayerId":2,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"3龙","againstResultState":2,"againstResult":"胜"},{"scheduleName":"要地地相辅相成要9","beginTime":1516069542000,"scheduleState":1,"againstName":"9龙对阵10龙","redPlayerId":2,"bluePlayerId":1,"redPlayerName":null,"bluePlayerName":null,"againstPlayer":"一龙","againstResultState":1,"againstResult":"胜"}]
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
         * scheduleName : 疯狂的石头3
         * beginTime : 1516774300000
         * scheduleState : 3
         * againstName : 9龙对阵10龙
         * redPlayerId : 1
         * bluePlayerId : 2
         * redPlayerName : null
         * bluePlayerName : null
         * againstPlayer : 一龙
         * againstResultState : 1
         * againstResult : 负
         */
        private String scheduleId;
        private String winPlayer;

        public String getWinPlayer() {
            return winPlayer;
        }

        public void setWinPlayer(String winPlayer) {
            this.winPlayer = winPlayer;
        }

        private String winWay;
        public String getWinWay() {
            return winWay;
        }

        public void setWinWay(String winWay) {
            this.winWay = winWay;
        }

        public String getScheduleId() {
            return scheduleId;
        }


        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getPublicityImg() {
            return publicityImg;
        }

        public void setPublicityImg(String publicityImg) {
            this.publicityImg = publicityImg;
        }

        private String publicityImg;
        private String scheduleName;
        private long beginTime;
        private int scheduleState;
        private String againstName;
        private int redPlayerId;
        private int bluePlayerId;
        private Object redPlayerName;
        private Object bluePlayerName;
        private String againstPlayer;
        private int againstResultState;
        private String againstResult;

        public String getScheduleName() {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public int getScheduleState() {
            return scheduleState;
        }

        public void setScheduleState(int scheduleState) {
            this.scheduleState = scheduleState;
        }

        public String getAgainstName() {
            return againstName;
        }

        public void setAgainstName(String againstName) {
            this.againstName = againstName;
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

        public Object getRedPlayerName() {
            return redPlayerName;
        }

        public void setRedPlayerName(Object redPlayerName) {
            this.redPlayerName = redPlayerName;
        }

        public Object getBluePlayerName() {
            return bluePlayerName;
        }

        public void setBluePlayerName(Object bluePlayerName) {
            this.bluePlayerName = bluePlayerName;
        }

        public String getAgainstPlayer() {
            return againstPlayer;
        }

        public void setAgainstPlayer(String againstPlayer) {
            this.againstPlayer = againstPlayer;
        }

        public int getAgainstResultState() {
            return againstResultState;
        }

        public void setAgainstResultState(int againstResultState) {
            this.againstResultState = againstResultState;
        }

        public String getAgainstResult() {
            return againstResult;
        }

        public void setAgainstResult(String againstResult) {
            this.againstResult = againstResult;
        }
    }
}
