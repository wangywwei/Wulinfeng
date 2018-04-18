package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */

public class SengdaiVideoBean {


    /**
     * code : 1000
     * message : 省代对应的赛程回放
     * data : [{"scheduleId":13,"scheduleName":"2232","beginTime":1516809600000,"publicityImg":"2018012518494168470075.png"},{"scheduleId":14,"scheduleName":"2232","beginTime":1516809600000,"publicityImg":"2018012518494168470075.png"},{"scheduleId":2,"scheduleName":"互相要地有物相成地10","beginTime":1516776987000,"publicityImg":"2018012301.png"},{"scheduleId":6,"scheduleName":"疯狂的石头3","beginTime":1516774300000,"publicityImg":"4123454555.png"},{"scheduleId":7,"scheduleName":"赛事2对阵14","beginTime":1516774300000,"publicityImg":"4123454555.png"},{"scheduleId":8,"scheduleName":"赛事2对阵24","beginTime":1516774300000,"publicityImg":"4123454555.png"},{"scheduleId":9,"scheduleName":"赛事2对阵35","beginTime":1516774300000,"publicityImg":"4123454555.png"},{"scheduleId":10,"scheduleName":"赛事2对阵36","beginTime":1516774300000,"publicityImg":"2018012202.png"},{"scheduleId":11,"scheduleName":"赛事2对阵37","beginTime":1516774300000,"publicityImg":"2018012303.png"},{"scheduleId":12,"scheduleName":"赛事2对阵38","beginTime":1516774300000,"publicityImg":"4123454555.png"}]
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
         * scheduleId : 13
         * scheduleName : 2232
         * beginTime : 1516809600000
         * publicityImg : 2018012518494168470075.png
         */

        private int scheduleId;
        private String scheduleName;
        private int playType;
        private String publicityImg;

        public int getPlayType() {
            return playType;
        }

        public void setPlayType(int playType) {
            this.playType = playType;
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


        public String getPublicityImg() {
            return publicityImg;
        }

        public void setPublicityImg(String publicityImg) {
            this.publicityImg = publicityImg;
        }
    }
}
