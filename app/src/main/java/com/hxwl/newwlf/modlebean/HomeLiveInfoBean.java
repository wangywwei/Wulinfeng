package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/1/24.
 */

public class HomeLiveInfoBean {


    /**
     * code : 1000
     * message : 赛程直播间
     * data : {"id":3,"competitionId":1,"scheduleName":"天涯要有产有要你好吗","publicityImg":"201801221889.jpg","liveUrl":"http://www.sohu.com","playerImg":"201801299345.jpg","prevueUrl":"http://ccc.mp4","playType":1,"holdAddress":"郑州市sl豪酒店","beginTime":1516531851000,"endTime":1516531856000,"state":3,"subscribeNum":345,"provinceAgentId":1,"provinceId":null,"cityId":null,"districtId":null,"isShow":1,"updateTime":1516618281000}
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
         * id : 3
         * competitionId : 1
         * scheduleName : 天涯要有产有要你好吗
         * publicityImg : 201801221889.jpg
         * liveUrl : http://www.sohu.com
         * playerImg : 201801299345.jpg
         * prevueUrl : http://ccc.mp4
         * playType : 1
         * holdAddress : 郑州市sl豪酒店
         * beginTime : 1516531851000
         * endTime : 1516531856000
         * state : 3
         * subscribeNum : 345
         * provinceAgentId : 1
         * provinceId : null
         * cityId : null
         * districtId : null
         * isShow : 1
         * updateTime : 1516618281000
         */

        private int id;
        private int competitionId;
        private String scheduleName;
        private String publicityImg;
        private String liveUrl;
        private String playerImg;
        private String prevueUrl;
        private int playType;
        private String holdAddress;
        private long beginTime;
        private long endTime;
        private int state;
        private int subscribeNum;
        private int provinceAgentId;
        private Object provinceId;
        private Object cityId;
        private Object districtId;
        private int isShow;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCompetitionId() {
            return competitionId;
        }

        public void setCompetitionId(int competitionId) {
            this.competitionId = competitionId;
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

        public String getLiveUrl() {
            return liveUrl;
        }

        public void setLiveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
        }

        public String getPlayerImg() {
            return playerImg;
        }

        public void setPlayerImg(String playerImg) {
            this.playerImg = playerImg;
        }

        public String getPrevueUrl() {
            return prevueUrl;
        }

        public void setPrevueUrl(String prevueUrl) {
            this.prevueUrl = prevueUrl;
        }

        public int getPlayType() {
            return playType;
        }

        public void setPlayType(int playType) {
            this.playType = playType;
        }

        public String getHoldAddress() {
            return holdAddress;
        }

        public void setHoldAddress(String holdAddress) {
            this.holdAddress = holdAddress;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSubscribeNum() {
            return subscribeNum;
        }

        public void setSubscribeNum(int subscribeNum) {
            this.subscribeNum = subscribeNum;
        }

        public int getProvinceAgentId() {
            return provinceAgentId;
        }

        public void setProvinceAgentId(int provinceAgentId) {
            this.provinceAgentId = provinceAgentId;
        }

        public Object getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Object provinceId) {
            this.provinceId = provinceId;
        }

        public Object getCityId() {
            return cityId;
        }

        public void setCityId(Object cityId) {
            this.cityId = cityId;
        }

        public Object getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Object districtId) {
            this.districtId = districtId;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
